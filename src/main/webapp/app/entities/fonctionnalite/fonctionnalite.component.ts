import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFonctionnalite } from 'app/shared/model/fonctionnalite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FonctionnaliteService } from './fonctionnalite.service';
import { FonctionnaliteDeleteDialogComponent } from './fonctionnalite-delete-dialog.component';

@Component({
  selector: 'jhi-fonctionnalite',
  templateUrl: './fonctionnalite.component.html'
})
export class FonctionnaliteComponent implements OnInit, OnDestroy {
  fonctionnalites: IFonctionnalite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected fonctionnaliteService: FonctionnaliteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fonctionnalites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.fonctionnaliteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFonctionnalite[]>) => this.paginateFonctionnalites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.fonctionnalites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFonctionnalites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFonctionnalite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFonctionnalites(): void {
    this.eventSubscriber = this.eventManager.subscribe('fonctionnaliteListModification', () => this.reset());
  }

  delete(fonctionnalite: IFonctionnalite): void {
    const modalRef = this.modalService.open(FonctionnaliteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fonctionnalite = fonctionnalite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFonctionnalites(data: IFonctionnalite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.fonctionnalites.push(data[i]);
      }
    }
  }
}
