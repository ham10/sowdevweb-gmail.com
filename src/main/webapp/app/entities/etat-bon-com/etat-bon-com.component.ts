import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatBonComService } from './etat-bon-com.service';
import { EtatBonComDeleteDialogComponent } from './etat-bon-com-delete-dialog.component';

@Component({
  selector: 'jhi-etat-bon-com',
  templateUrl: './etat-bon-com.component.html'
})
export class EtatBonComComponent implements OnInit, OnDestroy {
  etatBonComs: IEtatBonCom[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatBonComService: EtatBonComService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatBonComs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatBonComService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatBonCom[]>) => this.paginateEtatBonComs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatBonComs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatBonComs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatBonCom): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatBonComs(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatBonComListModification', () => this.reset());
  }

  delete(etatBonCom: IEtatBonCom): void {
    const modalRef = this.modalService.open(EtatBonComDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatBonCom = etatBonCom;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatBonComs(data: IEtatBonCom[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatBonComs.push(data[i]);
      }
    }
  }
}
