import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDevise } from 'app/shared/model/devise.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DeviseService } from './devise.service';
import { DeviseDeleteDialogComponent } from './devise-delete-dialog.component';

@Component({
  selector: 'jhi-devise',
  templateUrl: './devise.component.html'
})
export class DeviseComponent implements OnInit, OnDestroy {
  devises: IDevise[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected deviseService: DeviseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.devises = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.deviseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDevise[]>) => this.paginateDevises(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.devises = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDevises();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDevise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDevises(): void {
    this.eventSubscriber = this.eventManager.subscribe('deviseListModification', () => this.reset());
  }

  delete(devise: IDevise): void {
    const modalRef = this.modalService.open(DeviseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.devise = devise;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDevises(data: IDevise[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.devises.push(data[i]);
      }
    }
  }
}
