import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeTarif } from 'app/shared/model/type-tarif.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeTarifService } from './type-tarif.service';
import { TypeTarifDeleteDialogComponent } from './type-tarif-delete-dialog.component';

@Component({
  selector: 'jhi-type-tarif',
  templateUrl: './type-tarif.component.html'
})
export class TypeTarifComponent implements OnInit, OnDestroy {
  typeTarifs: ITypeTarif[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeTarifService: TypeTarifService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeTarifs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeTarifService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeTarif[]>) => this.paginateTypeTarifs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeTarifs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeTarifs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeTarif): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeTarifs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeTarifListModification', () => this.reset());
  }

  delete(typeTarif: ITypeTarif): void {
    const modalRef = this.modalService.open(TypeTarifDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeTarif = typeTarif;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeTarifs(data: ITypeTarif[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeTarifs.push(data[i]);
      }
    }
  }
}
