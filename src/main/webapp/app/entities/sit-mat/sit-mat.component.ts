import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISitMat } from 'app/shared/model/sit-mat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SitMatService } from './sit-mat.service';
import { SitMatDeleteDialogComponent } from './sit-mat-delete-dialog.component';

@Component({
  selector: 'jhi-sit-mat',
  templateUrl: './sit-mat.component.html'
})
export class SitMatComponent implements OnInit, OnDestroy {
  sitMats: ISitMat[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sitMatService: SitMatService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sitMats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sitMatService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISitMat[]>) => this.paginateSitMats(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sitMats = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSitMats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISitMat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSitMats(): void {
    this.eventSubscriber = this.eventManager.subscribe('sitMatListModification', () => this.reset());
  }

  delete(sitMat: ISitMat): void {
    const modalRef = this.modalService.open(SitMatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sitMat = sitMat;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSitMats(data: ISitMat[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sitMats.push(data[i]);
      }
    }
  }
}
