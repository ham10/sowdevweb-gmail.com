import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnite } from 'app/shared/model/unite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UniteService } from './unite.service';
import { UniteDeleteDialogComponent } from './unite-delete-dialog.component';

@Component({
  selector: 'jhi-unite',
  templateUrl: './unite.component.html'
})
export class UniteComponent implements OnInit, OnDestroy {
  unites: IUnite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected uniteService: UniteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.unites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.uniteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IUnite[]>) => this.paginateUnites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.unites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUnites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUnite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUnites(): void {
    this.eventSubscriber = this.eventManager.subscribe('uniteListModification', () => this.reset());
  }

  delete(unite: IUnite): void {
    const modalRef = this.modalService.open(UniteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unite = unite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUnites(data: IUnite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.unites.push(data[i]);
      }
    }
  }
}
