import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestion } from 'app/shared/model/question.model';

type EntityResponseType = HttpResponse<IQuestion>;
type EntityArrayResponseType = HttpResponse<IQuestion[]>;

@Injectable({ providedIn: 'root' })
export class QuestionService {
  public resourceUrl = SERVER_API_URL + 'api/questions';

  constructor(protected http: HttpClient) {}

  create(question: IQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(question);
    return this.http
      .post<IQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(question: IQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(question);
    return this.http
      .put<IQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(question: IQuestion): IQuestion {
    const copy: IQuestion = Object.assign({}, question, {
      dateDeleted: question.dateDeleted && question.dateDeleted.isValid() ? question.dateDeleted.toJSON() : undefined,
      dateCreated: question.dateCreated && question.dateCreated.isValid() ? question.dateCreated.toJSON() : undefined,
      dateUpdated: question.dateUpdated && question.dateUpdated.isValid() ? question.dateUpdated.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((question: IQuestion) => {
        question.dateDeleted = question.dateDeleted ? moment(question.dateDeleted) : undefined;
        question.dateCreated = question.dateCreated ? moment(question.dateCreated) : undefined;
        question.dateUpdated = question.dateUpdated ? moment(question.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
