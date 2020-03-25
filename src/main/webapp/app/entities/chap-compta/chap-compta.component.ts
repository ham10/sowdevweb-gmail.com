import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChapCompta } from 'app/shared/model/chap-compta.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ChapComptaService } from './chap-compta.service';
import { ChapComptaDeleteDialogComponent } from './chap-compta-delete-dialog.component';

@Component({
  selector: 'jhi-chap-compta',
  templateUrl: './chap-compta.component.html'
})
export class ChapComptaComponent implements OnInit, OnDestroy {
  chapComptas: IChapCompta[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected chapComptaService: ChapComptaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.chapComptas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.chapComptaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IChapCompta[]>) => this.paginateChapComptas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.chapComptas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChapComptas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChapCompta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChapComptas(): void {
    this.eventSubscriber = this.eventManager.subscribe('chapComptaListModification', () => this.reset());
  }

  delete(chapCompta: IChapCompta): void {
    const modalRef = this.modalService.open(ChapComptaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chapCompta = chapCompta;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateChapComptas(data: IChapCompta[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.chapComptas.push(data[i]);
      }
    }
  }
}
