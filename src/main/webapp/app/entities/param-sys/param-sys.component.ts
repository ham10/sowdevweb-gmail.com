import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParamSys } from 'app/shared/model/param-sys.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ParamSysService } from './param-sys.service';
import { ParamSysDeleteDialogComponent } from './param-sys-delete-dialog.component';

@Component({
  selector: 'jhi-param-sys',
  templateUrl: './param-sys.component.html'
})
export class ParamSysComponent implements OnInit, OnDestroy {
  paramSys: IParamSys[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected paramSysService: ParamSysService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.paramSys = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.paramSysService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IParamSys[]>) => this.paginateParamSys(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.paramSys = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParamSys();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParamSys): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParamSys(): void {
    this.eventSubscriber = this.eventManager.subscribe('paramSysListModification', () => this.reset());
  }

  delete(paramSys: IParamSys): void {
    const modalRef = this.modalService.open(ParamSysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paramSys = paramSys;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateParamSys(data: IParamSys[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.paramSys.push(data[i]);
      }
    }
  }
}
