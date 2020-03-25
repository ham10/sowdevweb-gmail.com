import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICodeBudget } from 'app/shared/model/code-budget.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CodeBudgetService } from './code-budget.service';
import { CodeBudgetDeleteDialogComponent } from './code-budget-delete-dialog.component';

@Component({
  selector: 'jhi-code-budget',
  templateUrl: './code-budget.component.html'
})
export class CodeBudgetComponent implements OnInit, OnDestroy {
  codeBudgets: ICodeBudget[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected codeBudgetService: CodeBudgetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.codeBudgets = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.codeBudgetService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICodeBudget[]>) => this.paginateCodeBudgets(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.codeBudgets = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCodeBudgets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICodeBudget): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCodeBudgets(): void {
    this.eventSubscriber = this.eventManager.subscribe('codeBudgetListModification', () => this.reset());
  }

  delete(codeBudget: ICodeBudget): void {
    const modalRef = this.modalService.open(CodeBudgetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.codeBudget = codeBudget;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCodeBudgets(data: ICodeBudget[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.codeBudgets.push(data[i]);
      }
    }
  }
}
