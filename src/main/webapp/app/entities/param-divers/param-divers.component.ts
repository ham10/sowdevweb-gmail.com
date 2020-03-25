import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParamDivers } from 'app/shared/model/param-divers.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ParamDiversService } from './param-divers.service';
import { ParamDiversDeleteDialogComponent } from './param-divers-delete-dialog.component';

@Component({
  selector: 'jhi-param-divers',
  templateUrl: './param-divers.component.html'
})
export class ParamDiversComponent implements OnInit, OnDestroy {
  paramDivers: IParamDivers[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected paramDiversService: ParamDiversService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.paramDivers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.paramDiversService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IParamDivers[]>) => this.paginateParamDivers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.paramDivers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParamDivers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParamDivers): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParamDivers(): void {
    this.eventSubscriber = this.eventManager.subscribe('paramDiversListModification', () => this.reset());
  }

  delete(paramDivers: IParamDivers): void {
    const modalRef = this.modalService.open(ParamDiversDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paramDivers = paramDivers;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateParamDivers(data: IParamDivers[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.paramDivers.push(data[i]);
      }
    }
  }
}
