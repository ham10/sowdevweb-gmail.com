import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeUnite } from 'app/shared/model/type-unite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeUniteService } from './type-unite.service';
import { TypeUniteDeleteDialogComponent } from './type-unite-delete-dialog.component';

@Component({
  selector: 'jhi-type-unite',
  templateUrl: './type-unite.component.html'
})
export class TypeUniteComponent implements OnInit, OnDestroy {
  typeUnites: ITypeUnite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeUniteService: TypeUniteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeUnites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeUniteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeUnite[]>) => this.paginateTypeUnites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeUnites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeUnites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeUnite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeUnites(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeUniteListModification', () => this.reset());
  }

  delete(typeUnite: ITypeUnite): void {
    const modalRef = this.modalService.open(TypeUniteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeUnite = typeUnite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeUnites(data: ITypeUnite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeUnites.push(data[i]);
      }
    }
  }
}
