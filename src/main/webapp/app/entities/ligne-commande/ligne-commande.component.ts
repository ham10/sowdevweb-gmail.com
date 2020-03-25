import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCommande } from 'app/shared/model/ligne-commande.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LigneCommandeService } from './ligne-commande.service';
import { LigneCommandeDeleteDialogComponent } from './ligne-commande-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-commande',
  templateUrl: './ligne-commande.component.html'
})
export class LigneCommandeComponent implements OnInit, OnDestroy {
  ligneCommandes: ILigneCommande[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ligneCommandes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ligneCommandeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILigneCommande[]>) => this.paginateLigneCommandes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ligneCommandes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLigneCommandes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILigneCommande): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLigneCommandes(): void {
    this.eventSubscriber = this.eventManager.subscribe('ligneCommandeListModification', () => this.reset());
  }

  delete(ligneCommande: ILigneCommande): void {
    const modalRef = this.modalService.open(LigneCommandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneCommande = ligneCommande;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLigneCommandes(data: ILigneCommande[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ligneCommandes.push(data[i]);
      }
    }
  }
}
