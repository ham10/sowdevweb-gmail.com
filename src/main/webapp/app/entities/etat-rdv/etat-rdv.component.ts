import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatRdv } from 'app/shared/model/etat-rdv.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatRdvService } from './etat-rdv.service';
import { EtatRdvDeleteDialogComponent } from './etat-rdv-delete-dialog.component';

@Component({
  selector: 'jhi-etat-rdv',
  templateUrl: './etat-rdv.component.html'
})
export class EtatRdvComponent implements OnInit, OnDestroy {
  etatRdvs: IEtatRdv[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatRdvService: EtatRdvService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatRdvs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatRdvService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatRdv[]>) => this.paginateEtatRdvs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatRdvs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatRdvs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatRdv): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatRdvs(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatRdvListModification', () => this.reset());
  }

  delete(etatRdv: IEtatRdv): void {
    const modalRef = this.modalService.open(EtatRdvDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatRdv = etatRdv;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatRdvs(data: IEtatRdv[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatRdvs.push(data[i]);
      }
    }
  }
}
