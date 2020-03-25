import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeChamps } from 'app/shared/model/type-champs.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeChampsService } from './type-champs.service';
import { TypeChampsDeleteDialogComponent } from './type-champs-delete-dialog.component';

@Component({
  selector: 'jhi-type-champs',
  templateUrl: './type-champs.component.html'
})
export class TypeChampsComponent implements OnInit, OnDestroy {
  typeChamps: ITypeChamps[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeChampsService: TypeChampsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeChamps = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeChampsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeChamps[]>) => this.paginateTypeChamps(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeChamps = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeChamps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeChamps): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeChamps(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeChampsListModification', () => this.reset());
  }

  delete(typeChamps: ITypeChamps): void {
    const modalRef = this.modalService.open(TypeChampsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeChamps = typeChamps;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeChamps(data: ITypeChamps[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeChamps.push(data[i]);
      }
    }
  }
}
