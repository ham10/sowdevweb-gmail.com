import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILit } from 'app/shared/model/lit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LitService } from './lit.service';
import { LitDeleteDialogComponent } from './lit-delete-dialog.component';

@Component({
  selector: 'jhi-lit',
  templateUrl: './lit.component.html'
})
export class LitComponent implements OnInit, OnDestroy {
  lits: ILit[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected litService: LitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.lits = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.litService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILit[]>) => this.paginateLits(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.lits = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLits(): void {
    this.eventSubscriber = this.eventManager.subscribe('litListModification', () => this.reset());
  }

  delete(lit: ILit): void {
    const modalRef = this.modalService.open(LitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lit = lit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLits(data: ILit[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.lits.push(data[i]);
      }
    }
  }
}
