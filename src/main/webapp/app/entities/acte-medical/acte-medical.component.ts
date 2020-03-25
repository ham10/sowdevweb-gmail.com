import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActeMedical } from 'app/shared/model/acte-medical.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActeMedicalService } from './acte-medical.service';
import { ActeMedicalDeleteDialogComponent } from './acte-medical-delete-dialog.component';

@Component({
  selector: 'jhi-acte-medical',
  templateUrl: './acte-medical.component.html'
})
export class ActeMedicalComponent implements OnInit, OnDestroy {
  acteMedicals: IActeMedical[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected acteMedicalService: ActeMedicalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.acteMedicals = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.acteMedicalService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IActeMedical[]>) => this.paginateActeMedicals(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.acteMedicals = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInActeMedicals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActeMedical): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActeMedicals(): void {
    this.eventSubscriber = this.eventManager.subscribe('acteMedicalListModification', () => this.reset());
  }

  delete(acteMedical: IActeMedical): void {
    const modalRef = this.modalService.open(ActeMedicalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.acteMedical = acteMedical;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateActeMedicals(data: IActeMedical[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.acteMedicals.push(data[i]);
      }
    }
  }
}
