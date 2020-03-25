import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultatActe } from 'app/shared/model/resultat-acte.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ResultatActeService } from './resultat-acte.service';
import { ResultatActeDeleteDialogComponent } from './resultat-acte-delete-dialog.component';

@Component({
  selector: 'jhi-resultat-acte',
  templateUrl: './resultat-acte.component.html'
})
export class ResultatActeComponent implements OnInit, OnDestroy {
  resultatActes: IResultatActe[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected resultatActeService: ResultatActeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.resultatActes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.resultatActeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IResultatActe[]>) => this.paginateResultatActes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.resultatActes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInResultatActes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResultatActe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResultatActes(): void {
    this.eventSubscriber = this.eventManager.subscribe('resultatActeListModification', () => this.reset());
  }

  delete(resultatActe: IResultatActe): void {
    const modalRef = this.modalService.open(ResultatActeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultatActe = resultatActe;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateResultatActes(data: IResultatActe[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.resultatActes.push(data[i]);
      }
    }
  }
}
