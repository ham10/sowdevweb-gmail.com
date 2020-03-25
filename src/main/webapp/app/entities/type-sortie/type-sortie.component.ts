import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeSortie } from 'app/shared/model/type-sortie.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeSortieService } from './type-sortie.service';
import { TypeSortieDeleteDialogComponent } from './type-sortie-delete-dialog.component';

@Component({
  selector: 'jhi-type-sortie',
  templateUrl: './type-sortie.component.html'
})
export class TypeSortieComponent implements OnInit, OnDestroy {
  typeSorties: ITypeSortie[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeSortieService: TypeSortieService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeSorties = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeSortieService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeSortie[]>) => this.paginateTypeSorties(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeSorties = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeSorties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeSortie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeSorties(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeSortieListModification', () => this.reset());
  }

  delete(typeSortie: ITypeSortie): void {
    const modalRef = this.modalService.open(TypeSortieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeSortie = typeSortie;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeSorties(data: ITypeSortie[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeSorties.push(data[i]);
      }
    }
  }
}
