import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClasseProd } from 'app/shared/model/classe-prod.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ClasseProdService } from './classe-prod.service';
import { ClasseProdDeleteDialogComponent } from './classe-prod-delete-dialog.component';

@Component({
  selector: 'jhi-classe-prod',
  templateUrl: './classe-prod.component.html'
})
export class ClasseProdComponent implements OnInit, OnDestroy {
  classeProds: IClasseProd[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected classeProdService: ClasseProdService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.classeProds = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.classeProdService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IClasseProd[]>) => this.paginateClasseProds(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.classeProds = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClasseProds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClasseProd): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClasseProds(): void {
    this.eventSubscriber = this.eventManager.subscribe('classeProdListModification', () => this.reset());
  }

  delete(classeProd: IClasseProd): void {
    const modalRef = this.modalService.open(ClasseProdDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.classeProd = classeProd;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateClasseProds(data: IClasseProd[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.classeProds.push(data[i]);
      }
    }
  }
}
