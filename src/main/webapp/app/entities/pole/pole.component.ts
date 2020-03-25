import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPole } from 'app/shared/model/pole.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PoleService } from './pole.service';
import { PoleDeleteDialogComponent } from './pole-delete-dialog.component';

@Component({
  selector: 'jhi-pole',
  templateUrl: './pole.component.html'
})
export class PoleComponent implements OnInit, OnDestroy {
  poles: IPole[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected poleService: PoleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.poles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.poleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPole[]>) => this.paginatePoles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.poles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPole): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('poleListModification', () => this.reset());
  }

  delete(pole: IPole): void {
    const modalRef = this.modalService.open(PoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pole = pole;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePoles(data: IPole[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.poles.push(data[i]);
      }
    }
  }
}
