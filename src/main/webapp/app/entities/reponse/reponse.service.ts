import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReponse } from 'app/shared/model/reponse.model';

type EntityResponseType = HttpResponse<IReponse>;
type EntityArrayResponseType = HttpResponse<IReponse[]>;

@Injectable({ providedIn: 'root' })
export class ReponseService {
  public resourceUrl = SERVER_API_URL + 'api/reponses';

  constructor(protected http: HttpClient) {}

  create(reponse: IReponse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reponse);
    return this.http
      .post<IReponse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reponse: IReponse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reponse);
    return this.http
      .put<IReponse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReponse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReponse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reponse: IReponse): IReponse {
    const copy: IReponse = Object.assign({}, reponse, {
      dateDeleted: reponse.dateDeleted && reponse.dateDeleted.isValid() ? reponse.dateDeleted.toJSON() : undefined,
      dateCreated: reponse.dateCreated && reponse.dateCreated.isValid() ? reponse.dateCreated.toJSON() : undefined,
      dateUpdated: reponse.dateUpdated && reponse.dateUpdated.isValid() ? reponse.dateUpdated.toJSON() : undefined
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
      res.body.forEach((reponse: IReponse) => {
        reponse.dateDeleted = reponse.dateDeleted ? moment(reponse.dateDeleted) : undefined;
        reponse.dateCreated = reponse.dateCreated ? moment(reponse.dateCreated) : undefined;
        reponse.dateUpdated = reponse.dateUpdated ? moment(reponse.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
