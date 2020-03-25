import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlat } from 'app/shared/model/plat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PlatService } from './plat.service';
import { PlatDeleteDialogComponent } from './plat-delete-dialog.component';

@Component({
  selector: 'jhi-plat',
  templateUrl: './plat.component.html'
})
export class PlatComponent implements OnInit, OnDestroy {
  plats: IPlat[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected platService: PlatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.plats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.platService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPlat[]>) => this.paginatePlats(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.plats = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlats(): void {
    this.eventSubscriber = this.eventManager.subscribe('platListModification', () => this.reset());
  }

  delete(plat: IPlat): void {
    const modalRef = this.modalService.open(PlatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plat = plat;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePlats(data: IPlat[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.plats.push(data[i]);
      }
    }
  }
}
