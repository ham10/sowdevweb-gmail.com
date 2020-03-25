import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeReport } from 'app/shared/model/type-report.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeReportService } from './type-report.service';
import { TypeReportDeleteDialogComponent } from './type-report-delete-dialog.component';

@Component({
  selector: 'jhi-type-report',
  templateUrl: './type-report.component.html'
})
export class TypeReportComponent implements OnInit, OnDestroy {
  typeReports: ITypeReport[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeReportService: TypeReportService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeReports = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeReportService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeReport[]>) => this.paginateTypeReports(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeReports = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeReportListModification', () => this.reset());
  }

  delete(typeReport: ITypeReport): void {
    const modalRef = this.modalService.open(TypeReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeReport = typeReport;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeReports(data: ITypeReport[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeReports.push(data[i]);
      }
    }
  }
}
