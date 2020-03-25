import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMachAutorise } from 'app/shared/model/mach-autorise.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MachAutoriseService } from './mach-autorise.service';
import { MachAutoriseDeleteDialogComponent } from './mach-autorise-delete-dialog.component';

@Component({
  selector: 'jhi-mach-autorise',
  templateUrl: './mach-autorise.component.html'
})
export class MachAutoriseComponent implements OnInit, OnDestroy {
  machAutorises: IMachAutorise[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected machAutoriseService: MachAutoriseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.machAutorises = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.machAutoriseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMachAutorise[]>) => this.paginateMachAutorises(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.machAutorises = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMachAutorises();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMachAutorise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMachAutorises(): void {
    this.eventSubscriber = this.eventManager.subscribe('machAutoriseListModification', () => this.reset());
  }

  delete(machAutorise: IMachAutorise): void {
    const modalRef = this.modalService.open(MachAutoriseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.machAutorise = machAutorise;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMachAutorises(data: IMachAutorise[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.machAutorises.push(data[i]);
      }
    }
  }
}
