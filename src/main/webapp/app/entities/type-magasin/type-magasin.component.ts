import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeMagasin } from 'app/shared/model/type-magasin.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeMagasinService } from './type-magasin.service';
import { TypeMagasinDeleteDialogComponent } from './type-magasin-delete-dialog.component';

@Component({
  selector: 'jhi-type-magasin',
  templateUrl: './type-magasin.component.html'
})
export class TypeMagasinComponent implements OnInit, OnDestroy {
  typeMagasins: ITypeMagasin[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeMagasinService: TypeMagasinService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeMagasins = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeMagasinService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeMagasin[]>) => this.paginateTypeMagasins(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeMagasins = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeMagasins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeMagasin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeMagasins(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeMagasinListModification', () => this.reset());
  }

  delete(typeMagasin: ITypeMagasin): void {
    const modalRef = this.modalService.open(TypeMagasinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeMagasin = typeMagasin;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeMagasins(data: ITypeMagasin[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeMagasins.push(data[i]);
      }
    }
  }
}
