import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParamCode } from 'app/shared/model/param-code.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ParamCodeService } from './param-code.service';
import { ParamCodeDeleteDialogComponent } from './param-code-delete-dialog.component';

@Component({
  selector: 'jhi-param-code',
  templateUrl: './param-code.component.html'
})
export class ParamCodeComponent implements OnInit, OnDestroy {
  paramCodes: IParamCode[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected paramCodeService: ParamCodeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.paramCodes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.paramCodeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IParamCode[]>) => this.paginateParamCodes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.paramCodes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParamCodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParamCode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParamCodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('paramCodeListModification', () => this.reset());
  }

  delete(paramCode: IParamCode): void {
    const modalRef = this.modalService.open(ParamCodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paramCode = paramCode;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateParamCodes(data: IParamCode[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.paramCodes.push(data[i]);
      }
    }
  }
}
