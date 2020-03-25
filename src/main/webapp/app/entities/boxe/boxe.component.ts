import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBoxe } from 'app/shared/model/boxe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BoxeService } from './boxe.service';
import { BoxeDeleteDialogComponent } from './boxe-delete-dialog.component';

@Component({
  selector: 'jhi-boxe',
  templateUrl: './boxe.component.html'
})
export class BoxeComponent implements OnInit, OnDestroy {
  boxes: IBoxe[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected boxeService: BoxeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.boxes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.boxeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBoxe[]>) => this.paginateBoxes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.boxes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBoxes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBoxe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBoxes(): void {
    this.eventSubscriber = this.eventManager.subscribe('boxeListModification', () => this.reset());
  }

  delete(boxe: IBoxe): void {
    const modalRef = this.modalService.open(BoxeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.boxe = boxe;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBoxes(data: IBoxe[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.boxes.push(data[i]);
      }
    }
  }
}
