import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICodeBudget } from 'app/shared/model/code-budget.model';

type EntityResponseType = HttpResponse<ICodeBudget>;
type EntityArrayResponseType = HttpResponse<ICodeBudget[]>;

@Injectable({ providedIn: 'root' })
export class CodeBudgetService {
  public resourceUrl = SERVER_API_URL + 'api/code-budgets';

  constructor(protected http: HttpClient) {}

  create(codeBudget: ICodeBudget): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(codeBudget);
    return this.http
      .post<ICodeBudget>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(codeBudget: ICodeBudget): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(codeBudget);
    return this.http
      .put<ICodeBudget>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICodeBudget>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICodeBudget[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(codeBudget: ICodeBudget): ICodeBudget {
    const copy: ICodeBudget = Object.assign({}, codeBudget, {
      dateCreated: codeBudget.dateCreated && codeBudget.dateCreated.isValid() ? codeBudget.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: codeBudget.dateUpdated && codeBudget.dateUpdated.isValid() ? codeBudget.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: codeBudget.dateDeleted && codeBudget.dateDeleted.isValid() ? codeBudget.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((codeBudget: ICodeBudget) => {
        codeBudget.dateCreated = codeBudget.dateCreated ? moment(codeBudget.dateCreated) : undefined;
        codeBudget.dateUpdated = codeBudget.dateUpdated ? moment(codeBudget.dateUpdated) : undefined;
        codeBudget.dateDeleted = codeBudget.dateDeleted ? moment(codeBudget.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
