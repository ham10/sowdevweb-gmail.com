import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatReport } from 'app/shared/model/cat-report.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CatReportService } from './cat-report.service';
import { CatReportDeleteDialogComponent } from './cat-report-delete-dialog.component';

@Component({
  selector: 'jhi-cat-report',
  templateUrl: './cat-report.component.html'
})
export class CatReportComponent implements OnInit, OnDestroy {
  catReports: ICatReport[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected catReportService: CatReportService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.catReports = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.catReportService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICatReport[]>) => this.paginateCatReports(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.catReports = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCatReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICatReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('catReportListModification', () => this.reset());
  }

  delete(catReport: ICatReport): void {
    const modalRef = this.modalService.open(CatReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catReport = catReport;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCatReports(data: ICatReport[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.catReports.push(data[i]);
      }
    }
  }
}
