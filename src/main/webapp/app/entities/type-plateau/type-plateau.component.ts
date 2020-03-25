import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePlateau } from 'app/shared/model/type-plateau.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePlateauService } from './type-plateau.service';
import { TypePlateauDeleteDialogComponent } from './type-plateau-delete-dialog.component';

@Component({
  selector: 'jhi-type-plateau',
  templateUrl: './type-plateau.component.html'
})
export class TypePlateauComponent implements OnInit, OnDestroy {
  typePlateaus: ITypePlateau[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePlateauService: TypePlateauService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePlateaus = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePlateauService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePlateau[]>) => this.paginateTypePlateaus(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePlateaus = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePlateaus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePlateau): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePlateaus(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePlateauListModification', () => this.reset());
  }

  delete(typePlateau: ITypePlateau): void {
    const modalRef = this.modalService.open(TypePlateauDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePlateau = typePlateau;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePlateaus(data: ITypePlateau[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePlateaus.push(data[i]);
      }
    }
  }
}
