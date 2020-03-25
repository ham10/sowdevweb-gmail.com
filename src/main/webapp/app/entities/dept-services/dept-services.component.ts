import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeptServices } from 'app/shared/model/dept-services.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DeptServicesService } from './dept-services.service';
import { DeptServicesDeleteDialogComponent } from './dept-services-delete-dialog.component';

@Component({
  selector: 'jhi-dept-services',
  templateUrl: './dept-services.component.html'
})
export class DeptServicesComponent implements OnInit, OnDestroy {
  deptServices: IDeptServices[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected deptServicesService: DeptServicesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.deptServices = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.deptServicesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDeptServices[]>) => this.paginateDeptServices(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.deptServices = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeptServices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeptServices): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeptServices(): void {
    this.eventSubscriber = this.eventManager.subscribe('deptServicesListModification', () => this.reset());
  }

  delete(deptServices: IDeptServices): void {
    const modalRef = this.modalService.open(DeptServicesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deptServices = deptServices;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDeptServices(data: IDeptServices[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.deptServices.push(data[i]);
      }
    }
  }
}
