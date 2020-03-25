import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcriture } from 'app/shared/model/ecriture.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EcritureService } from './ecriture.service';
import { EcritureDeleteDialogComponent } from './ecriture-delete-dialog.component';

@Component({
  selector: 'jhi-ecriture',
  templateUrl: './ecriture.component.html'
})
export class EcritureComponent implements OnInit, OnDestroy {
  ecritures: IEcriture[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ecritureService: EcritureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ecritures = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ecritureService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEcriture[]>) => this.paginateEcritures(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ecritures = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEcritures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEcriture): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEcritures(): void {
    this.eventSubscriber = this.eventManager.subscribe('ecritureListModification', () => this.reset());
  }

  delete(ecriture: IEcriture): void {
    const modalRef = this.modalService.open(EcritureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ecriture = ecriture;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEcritures(data: IEcriture[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ecritures.push(data[i]);
      }
    }
  }
}
