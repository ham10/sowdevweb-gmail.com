import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeCaisse } from 'app/shared/model/type-caisse.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeCaisseService } from './type-caisse.service';
import { TypeCaisseDeleteDialogComponent } from './type-caisse-delete-dialog.component';

@Component({
  selector: 'jhi-type-caisse',
  templateUrl: './type-caisse.component.html'
})
export class TypeCaisseComponent implements OnInit, OnDestroy {
  typeCaisses: ITypeCaisse[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeCaisseService: TypeCaisseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeCaisses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeCaisseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeCaisse[]>) => this.paginateTypeCaisses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeCaisses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeCaisses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeCaisse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeCaisses(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeCaisseListModification', () => this.reset());
  }

  delete(typeCaisse: ITypeCaisse): void {
    const modalRef = this.modalService.open(TypeCaisseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeCaisse = typeCaisse;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeCaisses(data: ITypeCaisse[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeCaisses.push(data[i]);
      }
    }
  }
}
