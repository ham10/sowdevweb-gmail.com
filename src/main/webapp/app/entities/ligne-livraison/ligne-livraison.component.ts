import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LigneLivraisonService } from './ligne-livraison.service';
import { LigneLivraisonDeleteDialogComponent } from './ligne-livraison-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-livraison',
  templateUrl: './ligne-livraison.component.html'
})
export class LigneLivraisonComponent implements OnInit, OnDestroy {
  ligneLivraisons: ILigneLivraison[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ligneLivraisonService: LigneLivraisonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ligneLivraisons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ligneLivraisonService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILigneLivraison[]>) => this.paginateLigneLivraisons(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ligneLivraisons = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLigneLivraisons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILigneLivraison): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLigneLivraisons(): void {
    this.eventSubscriber = this.eventManager.subscribe('ligneLivraisonListModification', () => this.reset());
  }

  delete(ligneLivraison: ILigneLivraison): void {
    const modalRef = this.modalService.open(LigneLivraisonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneLivraison = ligneLivraison;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLigneLivraisons(data: ILigneLivraison[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ligneLivraisons.push(data[i]);
      }
    }
  }
}
