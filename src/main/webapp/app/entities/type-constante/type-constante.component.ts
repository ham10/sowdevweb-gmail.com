import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeConstante } from 'app/shared/model/type-constante.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeConstanteService } from './type-constante.service';
import { TypeConstanteDeleteDialogComponent } from './type-constante-delete-dialog.component';

@Component({
  selector: 'jhi-type-constante',
  templateUrl: './type-constante.component.html'
})
export class TypeConstanteComponent implements OnInit, OnDestroy {
  typeConstantes: ITypeConstante[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeConstanteService: TypeConstanteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeConstantes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeConstanteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeConstante[]>) => this.paginateTypeConstantes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeConstantes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeConstantes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeConstante): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeConstantes(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeConstanteListModification', () => this.reset());
  }

  delete(typeConstante: ITypeConstante): void {
    const modalRef = this.modalService.open(TypeConstanteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeConstante = typeConstante;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeConstantes(data: ITypeConstante[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeConstantes.push(data[i]);
      }
    }
  }
}
