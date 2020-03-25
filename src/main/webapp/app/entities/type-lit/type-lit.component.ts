import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeLit } from 'app/shared/model/type-lit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeLitService } from './type-lit.service';
import { TypeLitDeleteDialogComponent } from './type-lit-delete-dialog.component';

@Component({
  selector: 'jhi-type-lit',
  templateUrl: './type-lit.component.html'
})
export class TypeLitComponent implements OnInit, OnDestroy {
  typeLits: ITypeLit[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeLitService: TypeLitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeLits = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeLitService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeLit[]>) => this.paginateTypeLits(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeLits = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeLits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeLit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeLits(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeLitListModification', () => this.reset());
  }

  delete(typeLit: ITypeLit): void {
    const modalRef = this.modalService.open(TypeLitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeLit = typeLit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeLits(data: ITypeLit[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeLits.push(data[i]);
      }
    }
  }
}
