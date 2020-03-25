import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HospitalisationService } from './hospitalisation.service';
import { HospitalisationDeleteDialogComponent } from './hospitalisation-delete-dialog.component';

@Component({
  selector: 'jhi-hospitalisation',
  templateUrl: './hospitalisation.component.html'
})
export class HospitalisationComponent implements OnInit, OnDestroy {
  hospitalisations: IHospitalisation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected hospitalisationService: HospitalisationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.hospitalisations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.hospitalisationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IHospitalisation[]>) => this.paginateHospitalisations(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.hospitalisations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHospitalisations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHospitalisation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHospitalisations(): void {
    this.eventSubscriber = this.eventManager.subscribe('hospitalisationListModification', () => this.reset());
  }

  delete(hospitalisation: IHospitalisation): void {
    const modalRef = this.modalService.open(HospitalisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hospitalisation = hospitalisation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHospitalisations(data: IHospitalisation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.hospitalisations.push(data[i]);
      }
    }
  }
}
