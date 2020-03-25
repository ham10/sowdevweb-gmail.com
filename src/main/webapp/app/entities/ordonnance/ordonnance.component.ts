import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OrdonnanceService } from './ordonnance.service';
import { OrdonnanceDeleteDialogComponent } from './ordonnance-delete-dialog.component';

@Component({
  selector: 'jhi-ordonnance',
  templateUrl: './ordonnance.component.html'
})
export class OrdonnanceComponent implements OnInit, OnDestroy {
  ordonnances: IOrdonnance[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ordonnanceService: OrdonnanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ordonnances = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ordonnanceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOrdonnance[]>) => this.paginateOrdonnances(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ordonnances = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrdonnances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrdonnance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrdonnances(): void {
    this.eventSubscriber = this.eventManager.subscribe('ordonnanceListModification', () => this.reset());
  }

  delete(ordonnance: IOrdonnance): void {
    const modalRef = this.modalService.open(OrdonnanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ordonnance = ordonnance;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOrdonnances(data: IOrdonnance[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ordonnances.push(data[i]);
      }
    }
  }
}
