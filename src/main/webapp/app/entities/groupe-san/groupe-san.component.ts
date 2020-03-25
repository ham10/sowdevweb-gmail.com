import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGroupeSan } from 'app/shared/model/groupe-san.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GroupeSanService } from './groupe-san.service';
import { GroupeSanDeleteDialogComponent } from './groupe-san-delete-dialog.component';

@Component({
  selector: 'jhi-groupe-san',
  templateUrl: './groupe-san.component.html'
})
export class GroupeSanComponent implements OnInit, OnDestroy {
  groupeSans: IGroupeSan[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected groupeSanService: GroupeSanService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.groupeSans = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.groupeSanService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGroupeSan[]>) => this.paginateGroupeSans(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.groupeSans = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGroupeSans();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGroupeSan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGroupeSans(): void {
    this.eventSubscriber = this.eventManager.subscribe('groupeSanListModification', () => this.reset());
  }

  delete(groupeSan: IGroupeSan): void {
    const modalRef = this.modalService.open(GroupeSanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.groupeSan = groupeSan;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGroupeSans(data: IGroupeSan[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.groupeSans.push(data[i]);
      }
    }
  }
}
