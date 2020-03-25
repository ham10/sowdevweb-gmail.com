import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtablis } from 'app/shared/model/etablis.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtablisService } from './etablis.service';
import { EtablisDeleteDialogComponent } from './etablis-delete-dialog.component';

@Component({
  selector: 'jhi-etablis',
  templateUrl: './etablis.component.html'
})
export class EtablisComponent implements OnInit, OnDestroy {
  etablis: IEtablis[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etablisService: EtablisService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etablis = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etablisService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtablis[]>) => this.paginateEtablis(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etablis = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtablis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtablis): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtablis(): void {
    this.eventSubscriber = this.eventManager.subscribe('etablisListModification', () => this.reset());
  }

  delete(etablis: IEtablis): void {
    const modalRef = this.modalService.open(EtablisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etablis = etablis;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtablis(data: IEtablis[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etablis.push(data[i]);
      }
    }
  }
}
