import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourFerie } from 'app/shared/model/jour-ferie.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JourFerieService } from './jour-ferie.service';
import { JourFerieDeleteDialogComponent } from './jour-ferie-delete-dialog.component';

@Component({
  selector: 'jhi-jour-ferie',
  templateUrl: './jour-ferie.component.html'
})
export class JourFerieComponent implements OnInit, OnDestroy {
  jourFeries: IJourFerie[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected jourFerieService: JourFerieService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.jourFeries = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.jourFerieService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IJourFerie[]>) => this.paginateJourFeries(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.jourFeries = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJourFeries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJourFerie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJourFeries(): void {
    this.eventSubscriber = this.eventManager.subscribe('jourFerieListModification', () => this.reset());
  }

  delete(jourFerie: IJourFerie): void {
    const modalRef = this.modalService.open(JourFerieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jourFerie = jourFerie;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateJourFeries(data: IJourFerie[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.jourFeries.push(data[i]);
      }
    }
  }
}
