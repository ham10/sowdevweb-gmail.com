import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePole } from 'app/shared/model/type-pole.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePoleService } from './type-pole.service';
import { TypePoleDeleteDialogComponent } from './type-pole-delete-dialog.component';

@Component({
  selector: 'jhi-type-pole',
  templateUrl: './type-pole.component.html'
})
export class TypePoleComponent implements OnInit, OnDestroy {
  typePoles: ITypePole[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePoleService: TypePoleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePoles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePoleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePole[]>) => this.paginateTypePoles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePoles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePole): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePoleListModification', () => this.reset());
  }

  delete(typePole: ITypePole): void {
    const modalRef = this.modalService.open(TypePoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePole = typePole;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePoles(data: ITypePole[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePoles.push(data[i]);
      }
    }
  }
}
