import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntecedent } from 'app/shared/model/antecedent.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AntecedentService } from './antecedent.service';
import { AntecedentDeleteDialogComponent } from './antecedent-delete-dialog.component';

@Component({
  selector: 'jhi-antecedent',
  templateUrl: './antecedent.component.html'
})
export class AntecedentComponent implements OnInit, OnDestroy {
  antecedents: IAntecedent[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected antecedentService: AntecedentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.antecedents = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.antecedentService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAntecedent[]>) => this.paginateAntecedents(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.antecedents = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAntecedents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAntecedent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAntecedents(): void {
    this.eventSubscriber = this.eventManager.subscribe('antecedentListModification', () => this.reset());
  }

  delete(antecedent: IAntecedent): void {
    const modalRef = this.modalService.open(AntecedentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.antecedent = antecedent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAntecedents(data: IAntecedent[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.antecedents.push(data[i]);
      }
    }
  }
}
