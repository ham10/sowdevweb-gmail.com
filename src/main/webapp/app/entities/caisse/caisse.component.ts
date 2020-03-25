import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICaisse } from 'app/shared/model/caisse.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CaisseService } from './caisse.service';
import { CaisseDeleteDialogComponent } from './caisse-delete-dialog.component';

@Component({
  selector: 'jhi-caisse',
  templateUrl: './caisse.component.html'
})
export class CaisseComponent implements OnInit, OnDestroy {
  caisses: ICaisse[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected caisseService: CaisseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.caisses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.caisseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICaisse[]>) => this.paginateCaisses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.caisses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCaisses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICaisse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCaisses(): void {
    this.eventSubscriber = this.eventManager.subscribe('caisseListModification', () => this.reset());
  }

  delete(caisse: ICaisse): void {
    const modalRef = this.modalService.open(CaisseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.caisse = caisse;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCaisses(data: ICaisse[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.caisses.push(data[i]);
      }
    }
  }
}
