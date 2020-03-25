import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IImmo } from 'app/shared/model/immo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ImmoService } from './immo.service';
import { ImmoDeleteDialogComponent } from './immo-delete-dialog.component';

@Component({
  selector: 'jhi-immo',
  templateUrl: './immo.component.html'
})
export class ImmoComponent implements OnInit, OnDestroy {
  immos: IImmo[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected immoService: ImmoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.immos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.immoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IImmo[]>) => this.paginateImmos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.immos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInImmos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IImmo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInImmos(): void {
    this.eventSubscriber = this.eventManager.subscribe('immoListModification', () => this.reset());
  }

  delete(immo: IImmo): void {
    const modalRef = this.modalService.open(ImmoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.immo = immo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateImmos(data: IImmo[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.immos.push(data[i]);
      }
    }
  }
}
