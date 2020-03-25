import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeQuestion } from 'app/shared/model/type-question.model';

type EntityResponseType = HttpResponse<ITypeQuestion>;
type EntityArrayResponseType = HttpResponse<ITypeQuestion[]>;

@Injectable({ providedIn: 'root' })
export class TypeQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/type-questions';

  constructor(protected http: HttpClient) {}

  create(typeQuestion: ITypeQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeQuestion);
    return this.http
      .post<ITypeQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeQuestion: ITypeQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeQuestion);
    return this.http
      .put<ITypeQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeQuestion: ITypeQuestion): ITypeQuestion {
    const copy: ITypeQuestion = Object.assign({}, typeQuestion, {
      dateDeleted: typeQuestion.dateDeleted && typeQuestion.dateDeleted.isValid() ? typeQuestion.dateDeleted.toJSON() : undefined,
      dateCreated: typeQuestion.dateCreated && typeQuestion.dateCreated.isValid() ? typeQuestion.dateCreated.toJSON() : undefined,
      dateUpdated: typeQuestion.dateUpdated && typeQuestion.dateUpdated.isValid() ? typeQuestion.dateUpdated.toJSON() : undefined
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
      res.body.forEach((typeQuestion: ITypeQuestion) => {
        typeQuestion.dateDeleted = typeQuestion.dateDeleted ? moment(typeQuestion.dateDeleted) : undefined;
        typeQuestion.dateCreated = typeQuestion.dateCreated ? moment(typeQuestion.dateCreated) : undefined;
        typeQuestion.dateUpdated = typeQuestion.dateUpdated ? moment(typeQuestion.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
