import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatImmo } from 'app/shared/model/etat-immo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatImmoService } from './etat-immo.service';
import { EtatImmoDeleteDialogComponent } from './etat-immo-delete-dialog.component';

@Component({
  selector: 'jhi-etat-immo',
  templateUrl: './etat-immo.component.html'
})
export class EtatImmoComponent implements OnInit, OnDestroy {
  etatImmos: IEtatImmo[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatImmoService: EtatImmoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatImmos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatImmoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatImmo[]>) => this.paginateEtatImmos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatImmos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatImmos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatImmo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatImmos(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatImmoListModification', () => this.reset());
  }

  delete(etatImmo: IEtatImmo): void {
    const modalRef = this.modalService.open(EtatImmoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatImmo = etatImmo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatImmos(data: IEtatImmo[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatImmos.push(data[i]);
      }
    }
  }
}
