import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FicheMedicalService } from './fiche-medical.service';
import { FicheMedicalDeleteDialogComponent } from './fiche-medical-delete-dialog.component';

@Component({
  selector: 'jhi-fiche-medical',
  templateUrl: './fiche-medical.component.html'
})
export class FicheMedicalComponent implements OnInit, OnDestroy {
  ficheMedicals: IFicheMedical[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ficheMedicalService: FicheMedicalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ficheMedicals = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ficheMedicalService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFicheMedical[]>) => this.paginateFicheMedicals(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ficheMedicals = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFicheMedicals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFicheMedical): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFicheMedicals(): void {
    this.eventSubscriber = this.eventManager.subscribe('ficheMedicalListModification', () => this.reset());
  }

  delete(ficheMedical: IFicheMedical): void {
    const modalRef = this.modalService.open(FicheMedicalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ficheMedical = ficheMedical;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFicheMedicals(data: IFicheMedical[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ficheMedicals.push(data[i]);
      }
    }
  }
}
