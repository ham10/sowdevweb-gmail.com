import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBanque } from 'app/shared/model/banque.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BanqueService } from './banque.service';
import { BanqueDeleteDialogComponent } from './banque-delete-dialog.component';

@Component({
  selector: 'jhi-banque',
  templateUrl: './banque.component.html'
})
export class BanqueComponent implements OnInit, OnDestroy {
  banques: IBanque[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected banqueService: BanqueService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.banques = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.banqueService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBanque[]>) => this.paginateBanques(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.banques = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBanques();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBanque): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBanques(): void {
    this.eventSubscriber = this.eventManager.subscribe('banqueListModification', () => this.reset());
  }

  delete(banque: IBanque): void {
    const modalRef = this.modalService.open(BanqueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.banque = banque;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBanques(data: IBanque[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.banques.push(data[i]);
      }
    }
  }
}
