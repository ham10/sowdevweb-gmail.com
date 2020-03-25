import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChambre } from 'app/shared/model/chambre.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ChambreService } from './chambre.service';
import { ChambreDeleteDialogComponent } from './chambre-delete-dialog.component';

@Component({
  selector: 'jhi-chambre',
  templateUrl: './chambre.component.html'
})
export class ChambreComponent implements OnInit, OnDestroy {
  chambres: IChambre[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected chambreService: ChambreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.chambres = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.chambreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IChambre[]>) => this.paginateChambres(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.chambres = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChambres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChambre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChambres(): void {
    this.eventSubscriber = this.eventManager.subscribe('chambreListModification', () => this.reset());
  }

  delete(chambre: IChambre): void {
    const modalRef = this.modalService.open(ChambreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chambre = chambre;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateChambres(data: IChambre[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.chambres.push(data[i]);
      }
    }
  }
}
