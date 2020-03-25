import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeNotif } from 'app/shared/model/type-notif.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeNotifService } from './type-notif.service';
import { TypeNotifDeleteDialogComponent } from './type-notif-delete-dialog.component';

@Component({
  selector: 'jhi-type-notif',
  templateUrl: './type-notif.component.html'
})
export class TypeNotifComponent implements OnInit, OnDestroy {
  typeNotifs: ITypeNotif[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeNotifService: TypeNotifService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeNotifs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeNotifService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeNotif[]>) => this.paginateTypeNotifs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeNotifs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeNotifs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeNotif): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeNotifs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeNotifListModification', () => this.reset());
  }

  delete(typeNotif: ITypeNotif): void {
    const modalRef = this.modalService.open(TypeNotifDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeNotif = typeNotif;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeNotifs(data: ITypeNotif[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeNotifs.push(data[i]);
      }
    }
  }
}
