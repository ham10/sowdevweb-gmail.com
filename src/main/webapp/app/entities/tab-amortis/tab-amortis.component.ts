import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITabAmortis } from 'app/shared/model/tab-amortis.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TabAmortisService } from './tab-amortis.service';
import { TabAmortisDeleteDialogComponent } from './tab-amortis-delete-dialog.component';

@Component({
  selector: 'jhi-tab-amortis',
  templateUrl: './tab-amortis.component.html'
})
export class TabAmortisComponent implements OnInit, OnDestroy {
  tabAmortis: ITabAmortis[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tabAmortisService: TabAmortisService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tabAmortis = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tabAmortisService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITabAmortis[]>) => this.paginateTabAmortis(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tabAmortis = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTabAmortis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITabAmortis): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTabAmortis(): void {
    this.eventSubscriber = this.eventManager.subscribe('tabAmortisListModification', () => this.reset());
  }

  delete(tabAmortis: ITabAmortis): void {
    const modalRef = this.modalService.open(TabAmortisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tabAmortis = tabAmortis;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTabAmortis(data: ITabAmortis[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tabAmortis.push(data[i]);
      }
    }
  }
}
