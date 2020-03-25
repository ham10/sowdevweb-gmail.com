import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChapCompta } from 'app/shared/model/chap-compta.model';

type EntityResponseType = HttpResponse<IChapCompta>;
type EntityArrayResponseType = HttpResponse<IChapCompta[]>;

@Injectable({ providedIn: 'root' })
export class ChapComptaService {
  public resourceUrl = SERVER_API_URL + 'api/chap-comptas';

  constructor(protected http: HttpClient) {}

  create(chapCompta: IChapCompta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chapCompta);
    return this.http
      .post<IChapCompta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chapCompta: IChapCompta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chapCompta);
    return this.http
      .put<IChapCompta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChapCompta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChapCompta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chapCompta: IChapCompta): IChapCompta {
    const copy: IChapCompta = Object.assign({}, chapCompta, {
      dateCreated: chapCompta.dateCreated && chapCompta.dateCreated.isValid() ? chapCompta.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: chapCompta.dateUpdated && chapCompta.dateUpdated.isValid() ? chapCompta.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: chapCompta.dateDeleted && chapCompta.dateDeleted.isValid() ? chapCompta.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((chapCompta: IChapCompta) => {
        chapCompta.dateCreated = chapCompta.dateCreated ? moment(chapCompta.dateCreated) : undefined;
        chapCompta.dateUpdated = chapCompta.dateUpdated ? moment(chapCompta.dateUpdated) : undefined;
        chapCompta.dateDeleted = chapCompta.dateDeleted ? moment(chapCompta.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
