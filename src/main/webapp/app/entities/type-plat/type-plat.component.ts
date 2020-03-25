import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePlat } from 'app/shared/model/type-plat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePlatService } from './type-plat.service';
import { TypePlatDeleteDialogComponent } from './type-plat-delete-dialog.component';

@Component({
  selector: 'jhi-type-plat',
  templateUrl: './type-plat.component.html'
})
export class TypePlatComponent implements OnInit, OnDestroy {
  typePlats: ITypePlat[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePlatService: TypePlatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePlats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePlatService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePlat[]>) => this.paginateTypePlats(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePlats = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePlats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePlat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePlats(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePlatListModification', () => this.reset());
  }

  delete(typePlat: ITypePlat): void {
    const modalRef = this.modalService.open(TypePlatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePlat = typePlat;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePlats(data: ITypePlat[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePlats.push(data[i]);
      }
    }
  }
}
