import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReponse } from 'app/shared/model/reponse.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReponseService } from './reponse.service';
import { ReponseDeleteDialogComponent } from './reponse-delete-dialog.component';

@Component({
  selector: 'jhi-reponse',
  templateUrl: './reponse.component.html'
})
export class ReponseComponent implements OnInit, OnDestroy {
  reponses: IReponse[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected reponseService: ReponseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.reponses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.reponseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReponse[]>) => this.paginateReponses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.reponses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReponses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReponse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReponses(): void {
    this.eventSubscriber = this.eventManager.subscribe('reponseListModification', () => this.reset());
  }

  delete(reponse: IReponse): void {
    const modalRef = this.modalService.open(ReponseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reponse = reponse;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateReponses(data: IReponse[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.reponses.push(data[i]);
      }
    }
  }
}
