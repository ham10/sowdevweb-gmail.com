import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeBonComService } from './type-bon-com.service';
import { TypeBonComDeleteDialogComponent } from './type-bon-com-delete-dialog.component';

@Component({
  selector: 'jhi-type-bon-com',
  templateUrl: './type-bon-com.component.html'
})
export class TypeBonComComponent implements OnInit, OnDestroy {
  typeBonComs: ITypeBonCom[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeBonComService: TypeBonComService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeBonComs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeBonComService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeBonCom[]>) => this.paginateTypeBonComs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeBonComs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeBonComs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeBonCom): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeBonComs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeBonComListModification', () => this.reset());
  }

  delete(typeBonCom: ITypeBonCom): void {
    const modalRef = this.modalService.open(TypeBonComDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeBonCom = typeBonCom;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeBonComs(data: ITypeBonCom[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeBonComs.push(data[i]);
      }
    }
  }
}
