import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeFournis } from 'app/shared/model/type-fournis.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeFournisService } from './type-fournis.service';
import { TypeFournisDeleteDialogComponent } from './type-fournis-delete-dialog.component';

@Component({
  selector: 'jhi-type-fournis',
  templateUrl: './type-fournis.component.html'
})
export class TypeFournisComponent implements OnInit, OnDestroy {
  typeFournis: ITypeFournis[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeFournisService: TypeFournisService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeFournis = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeFournisService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeFournis[]>) => this.paginateTypeFournis(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeFournis = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeFournis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeFournis): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeFournis(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeFournisListModification', () => this.reset());
  }

  delete(typeFournis: ITypeFournis): void {
    const modalRef = this.modalService.open(TypeFournisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeFournis = typeFournis;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeFournis(data: ITypeFournis[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeFournis.push(data[i]);
      }
    }
  }
}
