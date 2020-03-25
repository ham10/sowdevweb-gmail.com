import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeFact } from 'app/shared/model/type-fact.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeFactService } from './type-fact.service';
import { TypeFactDeleteDialogComponent } from './type-fact-delete-dialog.component';

@Component({
  selector: 'jhi-type-fact',
  templateUrl: './type-fact.component.html'
})
export class TypeFactComponent implements OnInit, OnDestroy {
  typeFacts: ITypeFact[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeFactService: TypeFactService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeFacts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeFactService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeFact[]>) => this.paginateTypeFacts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeFacts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeFacts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeFact): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeFacts(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeFactListModification', () => this.reset());
  }

  delete(typeFact: ITypeFact): void {
    const modalRef = this.modalService.open(TypeFactDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeFact = typeFact;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeFacts(data: ITypeFact[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeFacts.push(data[i]);
      }
    }
  }
}
