import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeServices } from 'app/shared/model/type-services.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeServicesService } from './type-services.service';
import { TypeServicesDeleteDialogComponent } from './type-services-delete-dialog.component';

@Component({
  selector: 'jhi-type-services',
  templateUrl: './type-services.component.html'
})
export class TypeServicesComponent implements OnInit, OnDestroy {
  typeServices: ITypeServices[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeServicesService: TypeServicesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeServices = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeServicesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeServices[]>) => this.paginateTypeServices(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeServices = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeServices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeServices): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeServices(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeServicesListModification', () => this.reset());
  }

  delete(typeServices: ITypeServices): void {
    const modalRef = this.modalService.open(TypeServicesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeServices = typeServices;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeServices(data: ITypeServices[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeServices.push(data[i]);
      }
    }
  }
}
