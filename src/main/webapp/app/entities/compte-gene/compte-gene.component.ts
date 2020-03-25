import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompteGene } from 'app/shared/model/compte-gene.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompteGeneService } from './compte-gene.service';
import { CompteGeneDeleteDialogComponent } from './compte-gene-delete-dialog.component';

@Component({
  selector: 'jhi-compte-gene',
  templateUrl: './compte-gene.component.html'
})
export class CompteGeneComponent implements OnInit, OnDestroy {
  compteGenes: ICompteGene[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected compteGeneService: CompteGeneService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.compteGenes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.compteGeneService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICompteGene[]>) => this.paginateCompteGenes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.compteGenes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompteGenes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompteGene): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompteGenes(): void {
    this.eventSubscriber = this.eventManager.subscribe('compteGeneListModification', () => this.reset());
  }

  delete(compteGene: ICompteGene): void {
    const modalRef = this.modalService.open(CompteGeneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compteGene = compteGene;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCompteGenes(data: ICompteGene[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.compteGenes.push(data[i]);
      }
    }
  }
}
