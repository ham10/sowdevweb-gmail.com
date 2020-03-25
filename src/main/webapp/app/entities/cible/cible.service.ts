import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICible } from 'app/shared/model/cible.model';

type EntityResponseType = HttpResponse<ICible>;
type EntityArrayResponseType = HttpResponse<ICible[]>;

@Injectable({ providedIn: 'root' })
export class CibleService {
  public resourceUrl = SERVER_API_URL + 'api/cibles';

  constructor(protected http: HttpClient) {}

  create(cible: ICible): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cible);
    return this.http
      .post<ICible>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cible: ICible): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cible);
    return this.http
      .put<ICible>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICible>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICible[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cible: ICible): ICible {
    const copy: ICible = Object.assign({}, cible, {
      dateDeleted: cible.dateDeleted && cible.dateDeleted.isValid() ? cible.dateDeleted.toJSON() : undefined,
      dateCreated: cible.dateCreated && cible.dateCreated.isValid() ? cible.dateCreated.toJSON() : undefined,
      dateUpdated: cible.dateUpdated && cible.dateUpdated.isValid() ? cible.dateUpdated.toJSON() : undefined
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
      res.body.forEach((cible: ICible) => {
        cible.dateDeleted = cible.dateDeleted ? moment(cible.dateDeleted) : undefined;
        cible.dateCreated = cible.dateCreated ? moment(cible.dateCreated) : undefined;
        cible.dateUpdated = cible.dateUpdated ? moment(cible.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
