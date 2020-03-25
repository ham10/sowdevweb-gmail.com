import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeDoc } from 'app/shared/model/type-doc.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeDocService } from './type-doc.service';
import { TypeDocDeleteDialogComponent } from './type-doc-delete-dialog.component';

@Component({
  selector: 'jhi-type-doc',
  templateUrl: './type-doc.component.html'
})
export class TypeDocComponent implements OnInit, OnDestroy {
  typeDocs: ITypeDoc[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeDocService: TypeDocService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeDocs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeDocService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeDoc[]>) => this.paginateTypeDocs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeDocs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeDocs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeDoc): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeDocs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeDocListModification', () => this.reset());
  }

  delete(typeDoc: ITypeDoc): void {
    const modalRef = this.modalService.open(TypeDocDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeDoc = typeDoc;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeDocs(data: ITypeDoc[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeDocs.push(data[i]);
      }
    }
  }
}
