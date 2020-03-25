import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultatActe } from 'app/shared/model/resultat-acte.model';

type EntityResponseType = HttpResponse<IResultatActe>;
type EntityArrayResponseType = HttpResponse<IResultatActe[]>;

@Injectable({ providedIn: 'root' })
export class ResultatActeService {
  public resourceUrl = SERVER_API_URL + 'api/resultat-actes';

  constructor(protected http: HttpClient) {}

  create(resultatActe: IResultatActe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultatActe);
    return this.http
      .post<IResultatActe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resultatActe: IResultatActe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultatActe);
    return this.http
      .put<IResultatActe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResultatActe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResultatActe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resultatActe: IResultatActe): IResultatActe {
    const copy: IResultatActe = Object.assign({}, resultatActe, {
      date: resultatActe.date && resultatActe.date.isValid() ? resultatActe.date.format(DATE_FORMAT) : undefined,
      dateCreated:
        resultatActe.dateCreated && resultatActe.dateCreated.isValid() ? resultatActe.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: resultatActe.dateUpdated && resultatActe.dateUpdated.isValid() ? resultatActe.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((resultatActe: IResultatActe) => {
        resultatActe.date = resultatActe.date ? moment(resultatActe.date) : undefined;
        resultatActe.dateCreated = resultatActe.dateCreated ? moment(resultatActe.dateCreated) : undefined;
        resultatActe.dateUpdated = resultatActe.dateUpdated ? moment(resultatActe.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
