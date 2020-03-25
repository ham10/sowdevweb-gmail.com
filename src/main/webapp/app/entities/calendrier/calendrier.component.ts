import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICalendrier } from 'app/shared/model/calendrier.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CalendrierService } from './calendrier.service';
import { CalendrierDeleteDialogComponent } from './calendrier-delete-dialog.component';

@Component({
  selector: 'jhi-calendrier',
  templateUrl: './calendrier.component.html'
})
export class CalendrierComponent implements OnInit, OnDestroy {
  calendriers: ICalendrier[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected calendrierService: CalendrierService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.calendriers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.calendrierService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICalendrier[]>) => this.paginateCalendriers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.calendriers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCalendriers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICalendrier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCalendriers(): void {
    this.eventSubscriber = this.eventManager.subscribe('calendrierListModification', () => this.reset());
  }

  delete(calendrier: ICalendrier): void {
    const modalRef = this.modalService.open(CalendrierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.calendrier = calendrier;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCalendriers(data: ICalendrier[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.calendriers.push(data[i]);
      }
    }
  }
}
