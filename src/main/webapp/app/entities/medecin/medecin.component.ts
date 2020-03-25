import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedecin } from 'app/shared/model/medecin.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MedecinService } from './medecin.service';
import { MedecinDeleteDialogComponent } from './medecin-delete-dialog.component';

@Component({
  selector: 'jhi-medecin',
  templateUrl: './medecin.component.html'
})
export class MedecinComponent implements OnInit, OnDestroy {
  medecins: IMedecin[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected medecinService: MedecinService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.medecins = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.medecinService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMedecin[]>) => this.paginateMedecins(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.medecins = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedecins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedecin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedecins(): void {
    this.eventSubscriber = this.eventManager.subscribe('medecinListModification', () => this.reset());
  }

  delete(medecin: IMedecin): void {
    const modalRef = this.modalService.open(MedecinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medecin = medecin;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMedecins(data: IMedecin[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.medecins.push(data[i]);
      }
    }
  }
}
