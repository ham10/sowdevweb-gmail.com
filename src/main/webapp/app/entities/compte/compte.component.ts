import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompte } from 'app/shared/model/compte.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompteService } from './compte.service';
import { CompteDeleteDialogComponent } from './compte-delete-dialog.component';

@Component({
  selector: 'jhi-compte',
  templateUrl: './compte.component.html'
})
export class CompteComponent implements OnInit, OnDestroy {
  comptes: ICompte[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected compteService: CompteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.comptes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.compteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICompte[]>) => this.paginateComptes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.comptes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComptes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompte): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComptes(): void {
    this.eventSubscriber = this.eventManager.subscribe('compteListModification', () => this.reset());
  }

  delete(compte: ICompte): void {
    const modalRef = this.modalService.open(CompteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compte = compte;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateComptes(data: ICompte[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.comptes.push(data[i]);
      }
    }
  }
}
