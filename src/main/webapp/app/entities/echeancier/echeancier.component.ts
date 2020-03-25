import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcheancier } from 'app/shared/model/echeancier.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EcheancierService } from './echeancier.service';
import { EcheancierDeleteDialogComponent } from './echeancier-delete-dialog.component';

@Component({
  selector: 'jhi-echeancier',
  templateUrl: './echeancier.component.html'
})
export class EcheancierComponent implements OnInit, OnDestroy {
  echeanciers: IEcheancier[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected echeancierService: EcheancierService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.echeanciers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.echeancierService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEcheancier[]>) => this.paginateEcheanciers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.echeanciers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEcheanciers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEcheancier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEcheanciers(): void {
    this.eventSubscriber = this.eventManager.subscribe('echeancierListModification', () => this.reset());
  }

  delete(echeancier: IEcheancier): void {
    const modalRef = this.modalService.open(EcheancierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.echeancier = echeancier;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEcheanciers(data: IEcheancier[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.echeanciers.push(data[i]);
      }
    }
  }
}
