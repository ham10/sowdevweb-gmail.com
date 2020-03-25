import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepot } from 'app/shared/model/depot.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DepotService } from './depot.service';
import { DepotDeleteDialogComponent } from './depot-delete-dialog.component';

@Component({
  selector: 'jhi-depot',
  templateUrl: './depot.component.html'
})
export class DepotComponent implements OnInit, OnDestroy {
  depots: IDepot[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected depotService: DepotService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.depots = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.depotService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDepot[]>) => this.paginateDepots(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.depots = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDepots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDepot): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDepots(): void {
    this.eventSubscriber = this.eventManager.subscribe('depotListModification', () => this.reset());
  }

  delete(depot: IDepot): void {
    const modalRef = this.modalService.open(DepotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.depot = depot;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDepots(data: IDepot[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.depots.push(data[i]);
      }
    }
  }
}
