import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVaccin } from 'app/shared/model/vaccin.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VaccinService } from './vaccin.service';
import { VaccinDeleteDialogComponent } from './vaccin-delete-dialog.component';

@Component({
  selector: 'jhi-vaccin',
  templateUrl: './vaccin.component.html'
})
export class VaccinComponent implements OnInit, OnDestroy {
  vaccins: IVaccin[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected vaccinService: VaccinService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.vaccins = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.vaccinService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVaccin[]>) => this.paginateVaccins(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.vaccins = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVaccins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVaccin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVaccins(): void {
    this.eventSubscriber = this.eventManager.subscribe('vaccinListModification', () => this.reset());
  }

  delete(vaccin: IVaccin): void {
    const modalRef = this.modalService.open(VaccinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vaccin = vaccin;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVaccins(data: IVaccin[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.vaccins.push(data[i]);
      }
    }
  }
}
