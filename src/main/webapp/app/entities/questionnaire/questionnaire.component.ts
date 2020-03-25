import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionnaire } from 'app/shared/model/questionnaire.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { QuestionnaireService } from './questionnaire.service';
import { QuestionnaireDeleteDialogComponent } from './questionnaire-delete-dialog.component';

@Component({
  selector: 'jhi-questionnaire',
  templateUrl: './questionnaire.component.html'
})
export class QuestionnaireComponent implements OnInit, OnDestroy {
  questionnaires: IQuestionnaire[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected questionnaireService: QuestionnaireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.questionnaires = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.questionnaireService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IQuestionnaire[]>) => this.paginateQuestionnaires(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.questionnaires = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionnaires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionnaire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionnaires(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionnaireListModification', () => this.reset());
  }

  delete(questionnaire: IQuestionnaire): void {
    const modalRef = this.modalService.open(QuestionnaireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionnaire = questionnaire;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateQuestionnaires(data: IQuestionnaire[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.questionnaires.push(data[i]);
      }
    }
  }
}
