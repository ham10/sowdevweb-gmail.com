import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypePatient } from 'app/shared/model/type-patient.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypePatientService } from './type-patient.service';
import { TypePatientDeleteDialogComponent } from './type-patient-delete-dialog.component';

@Component({
  selector: 'jhi-type-patient',
  templateUrl: './type-patient.component.html'
})
export class TypePatientComponent implements OnInit, OnDestroy {
  typePatients: ITypePatient[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typePatientService: TypePatientService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typePatients = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typePatientService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypePatient[]>) => this.paginateTypePatients(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typePatients = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypePatients();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypePatient): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypePatients(): void {
    this.eventSubscriber = this.eventManager.subscribe('typePatientListModification', () => this.reset());
  }

  delete(typePatient: ITypePatient): void {
    const modalRef = this.modalService.open(TypePatientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typePatient = typePatient;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypePatients(data: ITypePatient[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typePatients.push(data[i]);
      }
    }
  }
}
