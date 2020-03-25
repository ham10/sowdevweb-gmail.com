import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJour } from 'app/shared/model/jour.model';

type EntityResponseType = HttpResponse<IJour>;
type EntityArrayResponseType = HttpResponse<IJour[]>;

@Injectable({ providedIn: 'root' })
export class JourService {
  public resourceUrl = SERVER_API_URL + 'api/jours';

  constructor(protected http: HttpClient) {}

  create(jour: IJour): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jour);
    return this.http
      .post<IJour>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jour: IJour): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jour);
    return this.http
      .put<IJour>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJour>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJour[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jour: IJour): IJour {
    const copy: IJour = Object.assign({}, jour, {
      dateCreated: jour.dateCreated && jour.dateCreated.isValid() ? jour.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: jour.dateUpdated && jour.dateUpdated.isValid() ? jour.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jour: IJour) => {
        jour.dateCreated = jour.dateCreated ? moment(jour.dateCreated) : undefined;
        jour.dateUpdated = jour.dateUpdated ? moment(jour.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
