import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeProd } from 'app/shared/model/type-prod.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeProdService } from './type-prod.service';
import { TypeProdDeleteDialogComponent } from './type-prod-delete-dialog.component';

@Component({
  selector: 'jhi-type-prod',
  templateUrl: './type-prod.component.html'
})
export class TypeProdComponent implements OnInit, OnDestroy {
  typeProds: ITypeProd[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeProdService: TypeProdService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeProds = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeProdService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeProd[]>) => this.paginateTypeProds(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeProds = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeProds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeProd): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeProds(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeProdListModification', () => this.reset());
  }

  delete(typeProd: ITypeProd): void {
    const modalRef = this.modalService.open(TypeProdDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeProd = typeProd;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeProds(data: ITypeProd[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeProds.push(data[i]);
      }
    }
  }
}
