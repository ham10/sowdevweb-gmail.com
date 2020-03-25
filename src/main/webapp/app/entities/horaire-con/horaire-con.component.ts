import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoraireCon } from 'app/shared/model/horaire-con.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HoraireConService } from './horaire-con.service';
import { HoraireConDeleteDialogComponent } from './horaire-con-delete-dialog.component';

@Component({
  selector: 'jhi-horaire-con',
  templateUrl: './horaire-con.component.html'
})
export class HoraireConComponent implements OnInit, OnDestroy {
  horaireCons: IHoraireCon[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected horaireConService: HoraireConService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.horaireCons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.horaireConService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IHoraireCon[]>) => this.paginateHoraireCons(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.horaireCons = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHoraireCons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHoraireCon): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHoraireCons(): void {
    this.eventSubscriber = this.eventManager.subscribe('horaireConListModification', () => this.reset());
  }

  delete(horaireCon: IHoraireCon): void {
    const modalRef = this.modalService.open(HoraireConDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.horaireCon = horaireCon;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHoraireCons(data: IHoraireCon[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.horaireCons.push(data[i]);
      }
    }
  }
}
