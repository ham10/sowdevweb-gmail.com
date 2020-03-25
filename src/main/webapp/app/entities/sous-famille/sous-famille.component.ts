import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISousFamille } from 'app/shared/model/sous-famille.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SousFamilleService } from './sous-famille.service';
import { SousFamilleDeleteDialogComponent } from './sous-famille-delete-dialog.component';

@Component({
  selector: 'jhi-sous-famille',
  templateUrl: './sous-famille.component.html'
})
export class SousFamilleComponent implements OnInit, OnDestroy {
  sousFamilles: ISousFamille[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sousFamilleService: SousFamilleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sousFamilles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sousFamilleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISousFamille[]>) => this.paginateSousFamilles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sousFamilles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSousFamilles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISousFamille): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSousFamilles(): void {
    this.eventSubscriber = this.eventManager.subscribe('sousFamilleListModification', () => this.reset());
  }

  delete(sousFamille: ISousFamille): void {
    const modalRef = this.modalService.open(SousFamilleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sousFamille = sousFamille;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSousFamilles(data: ISousFamille[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sousFamilles.push(data[i]);
      }
    }
  }
}
