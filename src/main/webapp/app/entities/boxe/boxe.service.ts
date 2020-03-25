import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoxe } from 'app/shared/model/boxe.model';

type EntityResponseType = HttpResponse<IBoxe>;
type EntityArrayResponseType = HttpResponse<IBoxe[]>;

@Injectable({ providedIn: 'root' })
export class BoxeService {
  public resourceUrl = SERVER_API_URL + 'api/boxes';

  constructor(protected http: HttpClient) {}

  create(boxe: IBoxe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boxe);
    return this.http
      .post<IBoxe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boxe: IBoxe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boxe);
    return this.http
      .put<IBoxe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoxe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoxe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boxe: IBoxe): IBoxe {
    const copy: IBoxe = Object.assign({}, boxe, {
      dateCreated: boxe.dateCreated && boxe.dateCreated.isValid() ? boxe.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: boxe.dateUpdated && boxe.dateUpdated.isValid() ? boxe.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((boxe: IBoxe) => {
        boxe.dateCreated = boxe.dateCreated ? moment(boxe.dateCreated) : undefined;
        boxe.dateUpdated = boxe.dateUpdated ? moment(boxe.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
