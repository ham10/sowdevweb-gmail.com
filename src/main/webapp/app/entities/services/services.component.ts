import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServices } from 'app/shared/model/services.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServicesService } from './services.service';
import { ServicesDeleteDialogComponent } from './services-delete-dialog.component';

@Component({
  selector: 'jhi-services',
  templateUrl: './services.component.html'
})
export class ServicesComponent implements OnInit, OnDestroy {
  services: IServices[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected servicesService: ServicesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.services = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.servicesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IServices[]>) => this.paginateServices(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.services = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServices): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServices(): void {
    this.eventSubscriber = this.eventManager.subscribe('servicesListModification', () => this.reset());
  }

  delete(services: IServices): void {
    const modalRef = this.modalService.open(ServicesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.services = services;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateServices(data: IServices[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.services.push(data[i]);
      }
    }
  }
}
