import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatCaisse } from 'app/shared/model/etat-caisse.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatCaisseService } from './etat-caisse.service';
import { EtatCaisseDeleteDialogComponent } from './etat-caisse-delete-dialog.component';

@Component({
  selector: 'jhi-etat-caisse',
  templateUrl: './etat-caisse.component.html'
})
export class EtatCaisseComponent implements OnInit, OnDestroy {
  etatCaisses: IEtatCaisse[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatCaisseService: EtatCaisseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatCaisses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatCaisseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatCaisse[]>) => this.paginateEtatCaisses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatCaisses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatCaisses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatCaisse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatCaisses(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatCaisseListModification', () => this.reset());
  }

  delete(etatCaisse: IEtatCaisse): void {
    const modalRef = this.modalService.open(EtatCaisseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatCaisse = etatCaisse;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatCaisses(data: IEtatCaisse[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatCaisses.push(data[i]);
      }
    }
  }
}
