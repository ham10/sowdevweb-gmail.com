import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatMagasin } from 'app/shared/model/cat-magasin.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CatMagasinService } from './cat-magasin.service';
import { CatMagasinDeleteDialogComponent } from './cat-magasin-delete-dialog.component';

@Component({
  selector: 'jhi-cat-magasin',
  templateUrl: './cat-magasin.component.html'
})
export class CatMagasinComponent implements OnInit, OnDestroy {
  catMagasins: ICatMagasin[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected catMagasinService: CatMagasinService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.catMagasins = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.catMagasinService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICatMagasin[]>) => this.paginateCatMagasins(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.catMagasins = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCatMagasins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICatMagasin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatMagasins(): void {
    this.eventSubscriber = this.eventManager.subscribe('catMagasinListModification', () => this.reset());
  }

  delete(catMagasin: ICatMagasin): void {
    const modalRef = this.modalService.open(CatMagasinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catMagasin = catMagasin;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCatMagasins(data: ICatMagasin[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.catMagasins.push(data[i]);
      }
    }
  }
}
