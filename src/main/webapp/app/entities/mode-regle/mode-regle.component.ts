import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeRegle } from 'app/shared/model/mode-regle.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ModeRegleService } from './mode-regle.service';
import { ModeRegleDeleteDialogComponent } from './mode-regle-delete-dialog.component';

@Component({
  selector: 'jhi-mode-regle',
  templateUrl: './mode-regle.component.html'
})
export class ModeRegleComponent implements OnInit, OnDestroy {
  modeRegles: IModeRegle[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected modeRegleService: ModeRegleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.modeRegles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.modeRegleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IModeRegle[]>) => this.paginateModeRegles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.modeRegles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModeRegles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModeRegle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModeRegles(): void {
    this.eventSubscriber = this.eventManager.subscribe('modeRegleListModification', () => this.reset());
  }

  delete(modeRegle: IModeRegle): void {
    const modalRef = this.modalService.open(ModeRegleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modeRegle = modeRegle;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateModeRegles(data: IModeRegle[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.modeRegles.push(data[i]);
      }
    }
  }
}
