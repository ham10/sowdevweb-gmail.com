import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeSoins } from 'app/shared/model/type-soins.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeSoinsService } from './type-soins.service';
import { TypeSoinsDeleteDialogComponent } from './type-soins-delete-dialog.component';

@Component({
  selector: 'jhi-type-soins',
  templateUrl: './type-soins.component.html'
})
export class TypeSoinsComponent implements OnInit, OnDestroy {
  typeSoins: ITypeSoins[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeSoinsService: TypeSoinsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeSoins = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeSoinsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeSoins[]>) => this.paginateTypeSoins(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeSoins = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeSoins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeSoins): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeSoins(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeSoinsListModification', () => this.reset());
  }

  delete(typeSoins: ITypeSoins): void {
    const modalRef = this.modalService.open(TypeSoinsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeSoins = typeSoins;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeSoins(data: ITypeSoins[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeSoins.push(data[i]);
      }
    }
  }
}
