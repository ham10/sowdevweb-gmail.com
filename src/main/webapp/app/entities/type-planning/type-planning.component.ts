import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePlanning } from 'app/shared/model/type-planning.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePlanningService } from './type-planning.service';
import { TypePlanningDeleteDialogComponent } from './type-planning-delete-dialog.component';

@Component({
  selector: 'jhi-type-planning',
  templateUrl: './type-planning.component.html'
})
export class TypePlanningComponent implements OnInit, OnDestroy {
  typePlannings: ITypePlanning[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePlanningService: TypePlanningService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePlannings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePlanningService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePlanning[]>) => this.paginateTypePlannings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePlannings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePlannings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePlanning): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePlannings(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePlanningListModification', () => this.reset());
  }

  delete(typePlanning: ITypePlanning): void {
    const modalRef = this.modalService.open(TypePlanningDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePlanning = typePlanning;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePlannings(data: ITypePlanning[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePlannings.push(data[i]);
      }
    }
  }
}
