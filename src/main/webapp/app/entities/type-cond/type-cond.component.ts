import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeCond } from 'app/shared/model/type-cond.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeCondService } from './type-cond.service';
import { TypeCondDeleteDialogComponent } from './type-cond-delete-dialog.component';

@Component({
  selector: 'jhi-type-cond',
  templateUrl: './type-cond.component.html'
})
export class TypeCondComponent implements OnInit, OnDestroy {
  typeConds: ITypeCond[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeCondService: TypeCondService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeConds = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeCondService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeCond[]>) => this.paginateTypeConds(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeConds = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeConds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeCond): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeConds(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeCondListModification', () => this.reset());
  }

  delete(typeCond: ITypeCond): void {
    const modalRef = this.modalService.open(TypeCondDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeCond = typeCond;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeConds(data: ITypeCond[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeConds.push(data[i]);
      }
    }
  }
}
