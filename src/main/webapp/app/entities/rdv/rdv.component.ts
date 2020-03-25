import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRDV } from 'app/shared/model/rdv.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RDVService } from './rdv.service';
import { RDVDeleteDialogComponent } from './rdv-delete-dialog.component';

@Component({
  selector: 'jhi-rdv',
  templateUrl: './rdv.component.html'
})
export class RDVComponent implements OnInit, OnDestroy {
  rDVS: IRDV[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rDVService: RDVService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rDVS = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rDVService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRDV[]>) => this.paginateRDVS(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rDVS = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRDVS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRDV): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRDVS(): void {
    this.eventSubscriber = this.eventManager.subscribe('rDVListModification', () => this.reset());
  }

  delete(rDV: IRDV): void {
    const modalRef = this.modalService.open(RDVDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rDV = rDV;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRDVS(data: IRDV[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rDVS.push(data[i]);
      }
    }
  }
}
