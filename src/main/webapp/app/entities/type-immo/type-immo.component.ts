import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeImmo } from 'app/shared/model/type-immo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeImmoService } from './type-immo.service';
import { TypeImmoDeleteDialogComponent } from './type-immo-delete-dialog.component';

@Component({
  selector: 'jhi-type-immo',
  templateUrl: './type-immo.component.html'
})
export class TypeImmoComponent implements OnInit, OnDestroy {
  typeImmos: ITypeImmo[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeImmoService: TypeImmoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeImmos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeImmoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeImmo[]>) => this.paginateTypeImmos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeImmos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeImmos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeImmo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeImmos(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeImmoListModification', () => this.reset());
  }

  delete(typeImmo: ITypeImmo): void {
    const modalRef = this.modalService.open(TypeImmoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeImmo = typeImmo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeImmos(data: ITypeImmo[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeImmos.push(data[i]);
      }
    }
  }
}
