import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtatPlanning } from 'app/shared/model/etat-planning.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtatPlanningService } from './etat-planning.service';
import { EtatPlanningDeleteDialogComponent } from './etat-planning-delete-dialog.component';

@Component({
  selector: 'jhi-etat-planning',
  templateUrl: './etat-planning.component.html'
})
export class EtatPlanningComponent implements OnInit, OnDestroy {
  etatPlannings: IEtatPlanning[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etatPlanningService: EtatPlanningService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.etatPlannings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.etatPlanningService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtatPlanning[]>) => this.paginateEtatPlannings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.etatPlannings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEtatPlannings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEtatPlanning): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEtatPlannings(): void {
    this.eventSubscriber = this.eventManager.subscribe('etatPlanningListModification', () => this.reset());
  }

  delete(etatPlanning: IEtatPlanning): void {
    const modalRef = this.modalService.open(EtatPlanningDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etatPlanning = etatPlanning;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtatPlannings(data: IEtatPlanning[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.etatPlannings.push(data[i]);
      }
    }
  }
}
