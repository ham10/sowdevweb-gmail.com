import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtagere } from 'app/shared/model/etagere.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtagereService } from './etagere.service';
import { EtagereDeleteDialogComponent } from './etagere-delete-dialog.component';

@Component({
  selector: 'jhi-etagere',
  templateUrl: './etagere.component.html'
})
export class EtagereComponent implements OnInit, OnDestroy {
  etageres: IEtagere[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etagereService: EtagereService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etageres = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etagereService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtagere[]>) => this.paginateEtageres(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etageres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtageres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtagere): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtageres(): void {
    this.eventSubscriber = this.eventManager.subscribe('etagereListModification', () => this.reset());
  }

  delete(etagere: IEtagere): void {
    const modalRef = this.modalService.open(EtagereDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etagere = etagere;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtageres(data: IEtagere[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etageres.push(data[i]);
      }
    }
  }
}
