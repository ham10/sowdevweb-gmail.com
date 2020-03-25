import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailPlanning } from 'app/shared/model/detail-planning.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DetailPlanningService } from './detail-planning.service';
import { DetailPlanningDeleteDialogComponent } from './detail-planning-delete-dialog.component';

@Component({
  selector: 'jhi-detail-planning',
  templateUrl: './detail-planning.component.html'
})
export class DetailPlanningComponent implements OnInit, OnDestroy {
  detailPlannings: IDetailPlanning[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected detailPlanningService: DetailPlanningService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.detailPlannings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.detailPlanningService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDetailPlanning[]>) => this.paginateDetailPlannings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.detailPlannings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDetailPlannings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDetailPlanning): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDetailPlannings(): void {
    this.eventSubscriber = this.eventManager.subscribe('detailPlanningListModification', () => this.reset());
  }

  delete(detailPlanning: IDetailPlanning): void {
    const modalRef = this.modalService.open(DetailPlanningDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detailPlanning = detailPlanning;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDetailPlannings(data: IDetailPlanning[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.detailPlannings.push(data[i]);
      }
    }
  }
}
