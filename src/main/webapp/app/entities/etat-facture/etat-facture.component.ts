import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatFacture } from 'app/shared/model/etat-facture.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatFactureService } from './etat-facture.service';
import { EtatFactureDeleteDialogComponent } from './etat-facture-delete-dialog.component';

@Component({
  selector: 'jhi-etat-facture',
  templateUrl: './etat-facture.component.html'
})
export class EtatFactureComponent implements OnInit, OnDestroy {
  etatFactures: IEtatFacture[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatFactureService: EtatFactureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatFactures = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatFactureService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatFacture[]>) => this.paginateEtatFactures(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatFactures = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatFactures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatFacture): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatFactures(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatFactureListModification', () => this.reset());
  }

  delete(etatFacture: IEtatFacture): void {
    const modalRef = this.modalService.open(EtatFactureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatFacture = etatFacture;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatFactures(data: IEtatFacture[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatFactures.push(data[i]);
      }
    }
  }
}
