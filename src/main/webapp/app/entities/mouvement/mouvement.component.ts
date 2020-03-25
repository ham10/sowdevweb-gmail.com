import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMouvement } from 'app/shared/model/mouvement.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MouvementService } from './mouvement.service';
import { MouvementDeleteDialogComponent } from './mouvement-delete-dialog.component';

@Component({
  selector: 'jhi-mouvement',
  templateUrl: './mouvement.component.html'
})
export class MouvementComponent implements OnInit, OnDestroy {
  mouvements: IMouvement[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected mouvementService: MouvementService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.mouvements = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.mouvementService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMouvement[]>) => this.paginateMouvements(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.mouvements = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMouvements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMouvement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMouvements(): void {
    this.eventSubscriber = this.eventManager.subscribe('mouvementListModification', () => this.reset());
  }

  delete(mouvement: IMouvement): void {
    const modalRef = this.modalService.open(MouvementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mouvement = mouvement;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMouvements(data: IMouvement[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.mouvements.push(data[i]);
      }
    }
  }
}
