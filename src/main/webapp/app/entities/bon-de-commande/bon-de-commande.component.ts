import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BonDeCommandeService } from './bon-de-commande.service';
import { BonDeCommandeDeleteDialogComponent } from './bon-de-commande-delete-dialog.component';

@Component({
  selector: 'jhi-bon-de-commande',
  templateUrl: './bon-de-commande.component.html'
})
export class BonDeCommandeComponent implements OnInit, OnDestroy {
  bonDeCommandes: IBonDeCommande[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected bonDeCommandeService: BonDeCommandeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.bonDeCommandes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.bonDeCommandeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBonDeCommande[]>) => this.paginateBonDeCommandes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.bonDeCommandes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBonDeCommandes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBonDeCommande): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBonDeCommandes(): void {
    this.eventSubscriber = this.eventManager.subscribe('bonDeCommandeListModification', () => this.reset());
  }

  delete(bonDeCommande: IBonDeCommande): void {
    const modalRef = this.modalService.open(BonDeCommandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bonDeCommande = bonDeCommande;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBonDeCommandes(data: IBonDeCommande[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.bonDeCommandes.push(data[i]);
      }
    }
  }
}
