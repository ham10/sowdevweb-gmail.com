import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeAntecedent } from 'app/shared/model/type-antecedent.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeAntecedentService } from './type-antecedent.service';
import { TypeAntecedentDeleteDialogComponent } from './type-antecedent-delete-dialog.component';

@Component({
  selector: 'jhi-type-antecedent',
  templateUrl: './type-antecedent.component.html'
})
export class TypeAntecedentComponent implements OnInit, OnDestroy {
  typeAntecedents: ITypeAntecedent[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeAntecedentService: TypeAntecedentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeAntecedents = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeAntecedentService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeAntecedent[]>) => this.paginateTypeAntecedents(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeAntecedents = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeAntecedents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeAntecedent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeAntecedents(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeAntecedentListModification', () => this.reset());
  }

  delete(typeAntecedent: ITypeAntecedent): void {
    const modalRef = this.modalService.open(TypeAntecedentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeAntecedent = typeAntecedent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeAntecedents(data: ITypeAntecedent[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeAntecedents.push(data[i]);
      }
    }
  }
}
