import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchemaCompta } from 'app/shared/model/schema-compta.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SchemaComptaService } from './schema-compta.service';
import { SchemaComptaDeleteDialogComponent } from './schema-compta-delete-dialog.component';

@Component({
  selector: 'jhi-schema-compta',
  templateUrl: './schema-compta.component.html'
})
export class SchemaComptaComponent implements OnInit, OnDestroy {
  schemaComptas: ISchemaCompta[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected schemaComptaService: SchemaComptaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.schemaComptas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.schemaComptaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISchemaCompta[]>) => this.paginateSchemaComptas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.schemaComptas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSchemaComptas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISchemaCompta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSchemaComptas(): void {
    this.eventSubscriber = this.eventManager.subscribe('schemaComptaListModification', () => this.reset());
  }

  delete(schemaCompta: ISchemaCompta): void {
    const modalRef = this.modalService.open(SchemaComptaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.schemaCompta = schemaCompta;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSchemaComptas(data: ISchemaCompta[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.schemaComptas.push(data[i]);
      }
    }
  }
}
