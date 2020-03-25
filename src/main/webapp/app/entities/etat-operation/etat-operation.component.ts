import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatOperation } from 'app/shared/model/etat-operation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatOperationService } from './etat-operation.service';
import { EtatOperationDeleteDialogComponent } from './etat-operation-delete-dialog.component';

@Component({
  selector: 'jhi-etat-operation',
  templateUrl: './etat-operation.component.html'
})
export class EtatOperationComponent implements OnInit, OnDestroy {
  etatOperations: IEtatOperation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatOperationService: EtatOperationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatOperations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatOperationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatOperation[]>) => this.paginateEtatOperations(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatOperations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatOperations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatOperation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatOperations(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatOperationListModification', () => this.reset());
  }

  delete(etatOperation: IEtatOperation): void {
    const modalRef = this.modalService.open(EtatOperationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatOperation = etatOperation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatOperations(data: IEtatOperation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatOperations.push(data[i]);
      }
    }
  }
}
