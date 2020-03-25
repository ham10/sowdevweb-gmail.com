import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarif } from 'app/shared/model/tarif.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifService } from './tarif.service';
import { TarifDeleteDialogComponent } from './tarif-delete-dialog.component';

@Component({
  selector: 'jhi-tarif',
  templateUrl: './tarif.component.html'
})
export class TarifComponent implements OnInit, OnDestroy {
  tarifs: ITarif[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tarifService: TarifService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tarifService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarif[]>) => this.paginateTarifs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tarifs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarif): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifs(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifListModification', () => this.reset());
  }

  delete(tarif: ITarif): void {
    const modalRef = this.modalService.open(TarifDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarif = tarif;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifs(data: ITarif[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tarifs.push(data[i]);
      }
    }
  }
}
