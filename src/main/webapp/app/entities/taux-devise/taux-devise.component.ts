import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITauxDevise } from 'app/shared/model/taux-devise.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TauxDeviseService } from './taux-devise.service';
import { TauxDeviseDeleteDialogComponent } from './taux-devise-delete-dialog.component';

@Component({
  selector: 'jhi-taux-devise',
  templateUrl: './taux-devise.component.html'
})
export class TauxDeviseComponent implements OnInit, OnDestroy {
  tauxDevises: ITauxDevise[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tauxDeviseService: TauxDeviseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tauxDevises = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tauxDeviseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITauxDevise[]>) => this.paginateTauxDevises(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tauxDevises = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTauxDevises();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITauxDevise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTauxDevises(): void {
    this.eventSubscriber = this.eventManager.subscribe('tauxDeviseListModification', () => this.reset());
  }

  delete(tauxDevise: ITauxDevise): void {
    const modalRef = this.modalService.open(TauxDeviseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tauxDevise = tauxDevise;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTauxDevises(data: ITauxDevise[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tauxDevises.push(data[i]);
      }
    }
  }
}
