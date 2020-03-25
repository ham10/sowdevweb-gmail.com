import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormeProd } from 'app/shared/model/forme-prod.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FormeProdService } from './forme-prod.service';
import { FormeProdDeleteDialogComponent } from './forme-prod-delete-dialog.component';

@Component({
  selector: 'jhi-forme-prod',
  templateUrl: './forme-prod.component.html'
})
export class FormeProdComponent implements OnInit, OnDestroy {
  formeProds: IFormeProd[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected formeProdService: FormeProdService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.formeProds = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.formeProdService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFormeProd[]>) => this.paginateFormeProds(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.formeProds = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFormeProds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFormeProd): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFormeProds(): void {
    this.eventSubscriber = this.eventManager.subscribe('formeProdListModification', () => this.reset());
  }

  delete(formeProd: IFormeProd): void {
    const modalRef = this.modalService.open(FormeProdDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.formeProd = formeProd;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFormeProds(data: IFormeProd[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.formeProds.push(data[i]);
      }
    }
  }
}
