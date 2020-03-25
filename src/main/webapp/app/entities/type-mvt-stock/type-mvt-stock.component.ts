import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeMvtStockService } from './type-mvt-stock.service';
import { TypeMvtStockDeleteDialogComponent } from './type-mvt-stock-delete-dialog.component';

@Component({
  selector: 'jhi-type-mvt-stock',
  templateUrl: './type-mvt-stock.component.html'
})
export class TypeMvtStockComponent implements OnInit, OnDestroy {
  typeMvtStocks: ITypeMvtStock[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeMvtStockService: TypeMvtStockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeMvtStocks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeMvtStockService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeMvtStock[]>) => this.paginateTypeMvtStocks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeMvtStocks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeMvtStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeMvtStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeMvtStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeMvtStockListModification', () => this.reset());
  }

  delete(typeMvtStock: ITypeMvtStock): void {
    const modalRef = this.modalService.open(TypeMvtStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeMvtStock = typeMvtStock;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeMvtStocks(data: ITypeMvtStock[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeMvtStocks.push(data[i]);
      }
    }
  }
}
