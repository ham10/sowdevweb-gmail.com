import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BonLivraisonService } from './bon-livraison.service';
import { BonLivraisonDeleteDialogComponent } from './bon-livraison-delete-dialog.component';

@Component({
  selector: 'jhi-bon-livraison',
  templateUrl: './bon-livraison.component.html'
})
export class BonLivraisonComponent implements OnInit, OnDestroy {
  bonLivraisons: IBonLivraison[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected bonLivraisonService: BonLivraisonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.bonLivraisons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.bonLivraisonService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBonLivraison[]>) => this.paginateBonLivraisons(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.bonLivraisons = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBonLivraisons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBonLivraison): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBonLivraisons(): void {
    this.eventSubscriber = this.eventManager.subscribe('bonLivraisonListModification', () => this.reset());
  }

  delete(bonLivraison: IBonLivraison): void {
    const modalRef = this.modalService.open(BonLivraisonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bonLivraison = bonLivraison;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBonLivraisons(data: IBonLivraison[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.bonLivraisons.push(data[i]);
      }
    }
  }
}
