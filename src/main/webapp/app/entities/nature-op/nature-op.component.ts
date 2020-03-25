import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureOp } from 'app/shared/model/nature-op.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NatureOpService } from './nature-op.service';
import { NatureOpDeleteDialogComponent } from './nature-op-delete-dialog.component';

@Component({
  selector: 'jhi-nature-op',
  templateUrl: './nature-op.component.html'
})
export class NatureOpComponent implements OnInit, OnDestroy {
  natureOps: INatureOp[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected natureOpService: NatureOpService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.natureOps = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.natureOpService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<INatureOp[]>) => this.paginateNatureOps(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.natureOps = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNatureOps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INatureOp): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNatureOps(): void {
    this.eventSubscriber = this.eventManager.subscribe('natureOpListModification', () => this.reset());
  }

  delete(natureOp: INatureOp): void {
    const modalRef = this.modalService.open(NatureOpDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.natureOp = natureOp;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNatureOps(data: INatureOp[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.natureOps.push(data[i]);
      }
    }
  }
}
