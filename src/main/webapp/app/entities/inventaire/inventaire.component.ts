import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventaire } from 'app/shared/model/inventaire.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InventaireService } from './inventaire.service';
import { InventaireDeleteDialogComponent } from './inventaire-delete-dialog.component';

@Component({
  selector: 'jhi-inventaire',
  templateUrl: './inventaire.component.html'
})
export class InventaireComponent implements OnInit, OnDestroy {
  inventaires: IInventaire[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected inventaireService: InventaireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.inventaires = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.inventaireService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInventaire[]>) => this.paginateInventaires(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.inventaires = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInventaires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInventaire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInventaires(): void {
    this.eventSubscriber = this.eventManager.subscribe('inventaireListModification', () => this.reset());
  }

  delete(inventaire: IInventaire): void {
    const modalRef = this.modalService.open(InventaireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inventaire = inventaire;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInventaires(data: IInventaire[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.inventaires.push(data[i]);
      }
    }
  }
}
