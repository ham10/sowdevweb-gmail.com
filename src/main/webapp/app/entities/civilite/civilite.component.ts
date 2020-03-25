import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICivilite } from 'app/shared/model/civilite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CiviliteService } from './civilite.service';
import { CiviliteDeleteDialogComponent } from './civilite-delete-dialog.component';

@Component({
  selector: 'jhi-civilite',
  templateUrl: './civilite.component.html'
})
export class CiviliteComponent implements OnInit, OnDestroy {
  civilites: ICivilite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected civiliteService: CiviliteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.civilites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.civiliteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICivilite[]>) => this.paginateCivilites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.civilites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCivilites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICivilite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCivilites(): void {
    this.eventSubscriber = this.eventManager.subscribe('civiliteListModification', () => this.reset());
  }

  delete(civilite: ICivilite): void {
    const modalRef = this.modalService.open(CiviliteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.civilite = civilite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCivilites(data: ICivilite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.civilites.push(data[i]);
      }
    }
  }
}
