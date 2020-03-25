import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeFacture } from 'app/shared/model/type-facture.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeFactureService } from './type-facture.service';
import { TypeFactureDeleteDialogComponent } from './type-facture-delete-dialog.component';

@Component({
  selector: 'jhi-type-facture',
  templateUrl: './type-facture.component.html'
})
export class TypeFactureComponent implements OnInit, OnDestroy {
  typeFactures: ITypeFacture[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeFactureService: TypeFactureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeFactures = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeFactureService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeFacture[]>) => this.paginateTypeFactures(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeFactures = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeFactures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeFacture): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeFactures(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeFactureListModification', () => this.reset());
  }

  delete(typeFacture: ITypeFacture): void {
    const modalRef = this.modalService.open(TypeFactureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeFacture = typeFacture;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeFactures(data: ITypeFacture[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeFactures.push(data[i]);
      }
    }
  }
}
