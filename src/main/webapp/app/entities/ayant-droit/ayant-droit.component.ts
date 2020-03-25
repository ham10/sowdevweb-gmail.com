import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAyantDroit } from 'app/shared/model/ayant-droit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AyantDroitService } from './ayant-droit.service';
import { AyantDroitDeleteDialogComponent } from './ayant-droit-delete-dialog.component';

@Component({
  selector: 'jhi-ayant-droit',
  templateUrl: './ayant-droit.component.html'
})
export class AyantDroitComponent implements OnInit, OnDestroy {
  ayantDroits: IAyantDroit[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ayantDroitService: AyantDroitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ayantDroits = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ayantDroitService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAyantDroit[]>) => this.paginateAyantDroits(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ayantDroits = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAyantDroits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAyantDroit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAyantDroits(): void {
    this.eventSubscriber = this.eventManager.subscribe('ayantDroitListModification', () => this.reset());
  }

  delete(ayantDroit: IAyantDroit): void {
    const modalRef = this.modalService.open(AyantDroitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ayantDroit = ayantDroit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAyantDroits(data: IAyantDroit[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ayantDroits.push(data[i]);
      }
    }
  }
}
