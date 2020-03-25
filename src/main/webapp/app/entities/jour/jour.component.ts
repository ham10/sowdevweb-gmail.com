import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJour } from 'app/shared/model/jour.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JourService } from './jour.service';
import { JourDeleteDialogComponent } from './jour-delete-dialog.component';

@Component({
  selector: 'jhi-jour',
  templateUrl: './jour.component.html'
})
export class JourComponent implements OnInit, OnDestroy {
  jours: IJour[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected jourService: JourService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.jours = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.jourService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IJour[]>) => this.paginateJours(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.jours = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJours(): void {
    this.eventSubscriber = this.eventManager.subscribe('jourListModification', () => this.reset());
  }

  delete(jour: IJour): void {
    const modalRef = this.modalService.open(JourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jour = jour;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateJours(data: IJour[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.jours.push(data[i]);
      }
    }
  }
}
