import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatChambre } from 'app/shared/model/cat-chambre.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CatChambreService } from './cat-chambre.service';
import { CatChambreDeleteDialogComponent } from './cat-chambre-delete-dialog.component';

@Component({
  selector: 'jhi-cat-chambre',
  templateUrl: './cat-chambre.component.html'
})
export class CatChambreComponent implements OnInit, OnDestroy {
  catChambres: ICatChambre[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected catChambreService: CatChambreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.catChambres = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.catChambreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICatChambre[]>) => this.paginateCatChambres(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.catChambres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCatChambres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICatChambre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatChambres(): void {
    this.eventSubscriber = this.eventManager.subscribe('catChambreListModification', () => this.reset());
  }

  delete(catChambre: ICatChambre): void {
    const modalRef = this.modalService.open(CatChambreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catChambre = catChambre;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCatChambres(data: ICatChambre[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.catChambres.push(data[i]);
      }
    }
  }
}
