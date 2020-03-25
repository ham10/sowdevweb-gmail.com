import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDosMedical } from 'app/shared/model/dos-medical.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DosMedicalService } from './dos-medical.service';
import { DosMedicalDeleteDialogComponent } from './dos-medical-delete-dialog.component';

@Component({
  selector: 'jhi-dos-medical',
  templateUrl: './dos-medical.component.html'
})
export class DosMedicalComponent implements OnInit, OnDestroy {
  dosMedicals: IDosMedical[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected dosMedicalService: DosMedicalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.dosMedicals = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.dosMedicalService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDosMedical[]>) => this.paginateDosMedicals(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.dosMedicals = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDosMedicals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDosMedical): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDosMedicals(): void {
    this.eventSubscriber = this.eventManager.subscribe('dosMedicalListModification', () => this.reset());
  }

  delete(dosMedical: IDosMedical): void {
    const modalRef = this.modalService.open(DosMedicalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dosMedical = dosMedical;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDosMedicals(data: IDosMedical[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.dosMedicals.push(data[i]);
      }
    }
  }
}
