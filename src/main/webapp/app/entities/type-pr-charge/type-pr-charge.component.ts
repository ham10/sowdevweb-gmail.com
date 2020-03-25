import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePrChargeService } from './type-pr-charge.service';
import { TypePrChargeDeleteDialogComponent } from './type-pr-charge-delete-dialog.component';

@Component({
  selector: 'jhi-type-pr-charge',
  templateUrl: './type-pr-charge.component.html'
})
export class TypePrChargeComponent implements OnInit, OnDestroy {
  typePrCharges: ITypePrCharge[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePrChargeService: TypePrChargeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePrCharges = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePrChargeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePrCharge[]>) => this.paginateTypePrCharges(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePrCharges = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePrCharges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePrCharge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePrCharges(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePrChargeListModification', () => this.reset());
  }

  delete(typePrCharge: ITypePrCharge): void {
    const modalRef = this.modalService.open(TypePrChargeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePrCharge = typePrCharge;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePrCharges(data: ITypePrCharge[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePrCharges.push(data[i]);
      }
    }
  }
}
