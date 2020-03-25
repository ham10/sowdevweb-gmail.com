import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeQuestion } from 'app/shared/model/type-question.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeQuestionService } from './type-question.service';
import { TypeQuestionDeleteDialogComponent } from './type-question-delete-dialog.component';

@Component({
  selector: 'jhi-type-question',
  templateUrl: './type-question.component.html'
})
export class TypeQuestionComponent implements OnInit, OnDestroy {
  typeQuestions: ITypeQuestion[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected typeQuestionService: TypeQuestionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.typeQuestions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.typeQuestionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeQuestion[]>) => this.paginateTypeQuestions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.typeQuestions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeQuestions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeQuestion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeQuestions(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeQuestionListModification', () => this.reset());
  }

  delete(typeQuestion: ITypeQuestion): void {
    const modalRef = this.modalService.open(TypeQuestionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeQuestion = typeQuestion;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTypeQuestions(data: ITypeQuestion[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.typeQuestions.push(data[i]);
      }
    }
  }
}
