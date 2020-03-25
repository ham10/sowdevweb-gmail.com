import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICible } from 'app/shared/model/cible.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CibleService } from './cible.service';
import { CibleDeleteDialogComponent } from './cible-delete-dialog.component';

@Component({
  selector: 'jhi-cible',
  templateUrl: './cible.component.html'
})
export class CibleComponent implements OnInit, OnDestroy {
  cibles: ICible[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected cibleService: CibleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.cibles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.cibleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICible[]>) => this.paginateCibles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.cibles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCibles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICible): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCibles(): void {
    this.eventSubscriber = this.eventManager.subscribe('cibleListModification', () => this.reset());
  }

  delete(cible: ICible): void {
    const modalRef = this.modalService.open(CibleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cible = cible;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCibles(data: ICible[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.cibles.push(data[i]);
      }
    }
  }
}
