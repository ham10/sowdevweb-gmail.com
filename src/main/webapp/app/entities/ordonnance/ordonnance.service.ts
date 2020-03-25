import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';

type EntityResponseType = HttpResponse<IOrdonnance>;
type EntityArrayResponseType = HttpResponse<IOrdonnance[]>;

@Injectable({ providedIn: 'root' })
export class OrdonnanceService {
  public resourceUrl = SERVER_API_URL + 'api/ordonnances';

  constructor(protected http: HttpClient) {}

  create(ordonnance: IOrdonnance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordonnance);
    return this.http
      .post<IOrdonnance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ordonnance: IOrdonnance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordonnance);
    return this.http
      .put<IOrdonnance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrdonnance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrdonnance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ordonnance: IOrdonnance): IOrdonnance {
    const copy: IOrdonnance = Object.assign({}, ordonnance, {
      date: ordonnance.date && ordonnance.date.isValid() ? ordonnance.date.format(DATE_FORMAT) : undefined,
      dateCreated: ordonnance.dateCreated && ordonnance.dateCreated.isValid() ? ordonnance.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: ordonnance.dateUpdated && ordonnance.dateUpdated.isValid() ? ordonnance.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ordonnance: IOrdonnance) => {
        ordonnance.date = ordonnance.date ? moment(ordonnance.date) : undefined;
        ordonnance.dateCreated = ordonnance.dateCreated ? moment(ordonnance.dateCreated) : undefined;
        ordonnance.dateUpdated = ordonnance.dateUpdated ? moment(ordonnance.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
