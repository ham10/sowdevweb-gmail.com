import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFamille } from 'app/shared/model/famille.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FamilleService } from './famille.service';
import { FamilleDeleteDialogComponent } from './famille-delete-dialog.component';

@Component({
  selector: 'jhi-famille',
  templateUrl: './famille.component.html'
})
export class FamilleComponent implements OnInit, OnDestroy {
  familles: IFamille[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected familleService: FamilleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.familles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.familleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFamille[]>) => this.paginateFamilles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.familles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFamilles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFamille): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFamilles(): void {
    this.eventSubscriber = this.eventManager.subscribe('familleListModification', () => this.reset());
  }

  delete(famille: IFamille): void {
    const modalRef = this.modalService.open(FamilleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.famille = famille;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFamilles(data: IFamille[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.familles.push(data[i]);
      }
    }
  }
}
