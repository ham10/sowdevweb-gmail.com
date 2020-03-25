import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMonnaie } from 'app/shared/model/monnaie.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MonnaieService } from './monnaie.service';
import { MonnaieDeleteDialogComponent } from './monnaie-delete-dialog.component';

@Component({
  selector: 'jhi-monnaie',
  templateUrl: './monnaie.component.html'
})
export class MonnaieComponent implements OnInit, OnDestroy {
  monnaies: IMonnaie[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected monnaieService: MonnaieService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.monnaies = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.monnaieService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMonnaie[]>) => this.paginateMonnaies(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.monnaies = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMonnaies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMonnaie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMonnaies(): void {
    this.eventSubscriber = this.eventManager.subscribe('monnaieListModification', () => this.reset());
  }

  delete(monnaie: IMonnaie): void {
    const modalRef = this.modalService.open(MonnaieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.monnaie = monnaie;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMonnaies(data: IMonnaie[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.monnaies.push(data[i]);
      }
    }
  }
}
