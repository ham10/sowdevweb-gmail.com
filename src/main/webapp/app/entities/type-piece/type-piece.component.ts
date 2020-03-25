import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePiece } from 'app/shared/model/type-piece.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePieceService } from './type-piece.service';
import { TypePieceDeleteDialogComponent } from './type-piece-delete-dialog.component';

@Component({
  selector: 'jhi-type-piece',
  templateUrl: './type-piece.component.html'
})
export class TypePieceComponent implements OnInit, OnDestroy {
  typePieces: ITypePiece[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePieceService: TypePieceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePieces = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePieceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePiece[]>) => this.paginateTypePieces(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePieces = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePieces();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePiece): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePieces(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePieceListModification', () => this.reset());
  }

  delete(typePiece: ITypePiece): void {
    const modalRef = this.modalService.open(TypePieceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePiece = typePiece;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePieces(data: ITypePiece[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePieces.push(data[i]);
      }
    }
  }
}
