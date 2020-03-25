import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegion } from 'app/shared/model/region.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RegionService } from './region.service';
import { RegionDeleteDialogComponent } from './region-delete-dialog.component';

@Component({
  selector: 'jhi-region',
  templateUrl: './region.component.html'
})
export class RegionComponent implements OnInit, OnDestroy {
  regions: IRegion[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected regionService: RegionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.regions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.regionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRegion[]>) => this.paginateRegions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.regions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRegions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRegion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRegions(): void {
    this.eventSubscriber = this.eventManager.subscribe('regionListModification', () => this.reset());
  }

  delete(region: IRegion): void {
    const modalRef = this.modalService.open(RegionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.region = region;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRegions(data: IRegion[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.regions.push(data[i]);
      }
    }
  }
}
