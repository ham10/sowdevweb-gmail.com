import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';

type EntityResponseType = HttpResponse<IQuestionnaire>;
type EntityArrayResponseType = HttpResponse<IQuestionnaire[]>;

@Injectable({ providedIn: 'root' })
export class QuestionnaireService {
  public resourceUrl = SERVER_API_URL + 'api/questionnaires';

  constructor(protected http: HttpClient) {}

  create(questionnaire: IQuestionnaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(questionnaire);
    return this.http
      .post<IQuestionnaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(questionnaire: IQuestionnaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(questionnaire);
    return this.http
      .put<IQuestionnaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuestionnaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuestionnaire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(questionnaire: IQuestionnaire): IQuestionnaire {
    const copy: IQuestionnaire = Object.assign({}, questionnaire, {
      dateDeleted: questionnaire.dateDeleted && questionnaire.dateDeleted.isValid() ? questionnaire.dateDeleted.toJSON() : undefined,
      dateCreated: questionnaire.dateCreated && questionnaire.dateCreated.isValid() ? questionnaire.dateCreated.toJSON() : undefined,
      dateUpdated: questionnaire.dateUpdated && questionnaire.dateUpdated.isValid() ? questionnaire.dateUpdated.toJSON() : undefined
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
      res.body.forEach((questionnaire: IQuestionnaire) => {
        questionnaire.dateDeleted = questionnaire.dateDeleted ? moment(questionnaire.dateDeleted) : undefined;
        questionnaire.dateCreated = questionnaire.dateCreated ? moment(questionnaire.dateCreated) : undefined;
        questionnaire.dateUpdated = questionnaire.dateUpdated ? moment(questionnaire.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
