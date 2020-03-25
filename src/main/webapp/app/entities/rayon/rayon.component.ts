import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRayon } from 'app/shared/model/rayon.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RayonService } from './rayon.service';
import { RayonDeleteDialogComponent } from './rayon-delete-dialog.component';

@Component({
  selector: 'jhi-rayon',
  templateUrl: './rayon.component.html'
})
export class RayonComponent implements OnInit, OnDestroy {
  rayons: IRayon[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rayonService: RayonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rayons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rayonService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRayon[]>) => this.paginateRayons(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rayons = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRayons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRayon): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRayons(): void {
    this.eventSubscriber = this.eventManager.subscribe('rayonListModification', () => this.reset());
  }

  delete(rayon: IRayon): void {
    const modalRef = this.modalService.open(RayonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rayon = rayon;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRayons(data: IRayon[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rayons.push(data[i]);
      }
    }
  }
}
