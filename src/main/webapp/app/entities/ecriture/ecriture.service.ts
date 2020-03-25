import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEcriture } from 'app/shared/model/ecriture.model';

type EntityResponseType = HttpResponse<IEcriture>;
type EntityArrayResponseType = HttpResponse<IEcriture[]>;

@Injectable({ providedIn: 'root' })
export class EcritureService {
  public resourceUrl = SERVER_API_URL + 'api/ecritures';

  constructor(protected http: HttpClient) {}

  create(ecriture: IEcriture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ecriture);
    return this.http
      .post<IEcriture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ecriture: IEcriture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ecriture);
    return this.http
      .put<IEcriture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEcriture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEcriture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ecriture: IEcriture): IEcriture {
    const copy: IEcriture = Object.assign({}, ecriture, {
      date: ecriture.date && ecriture.date.isValid() ? ecriture.date.format(DATE_FORMAT) : undefined,
      dateCreated: ecriture.dateCreated && ecriture.dateCreated.isValid() ? ecriture.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: ecriture.dateUpdated && ecriture.dateUpdated.isValid() ? ecriture.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: ecriture.dateDeleted && ecriture.dateDeleted.isValid() ? ecriture.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ecriture: IEcriture) => {
        ecriture.date = ecriture.date ? moment(ecriture.date) : undefined;
        ecriture.dateCreated = ecriture.dateCreated ? moment(ecriture.dateCreated) : undefined;
        ecriture.dateUpdated = ecriture.dateUpdated ? moment(ecriture.dateUpdated) : undefined;
        ecriture.dateDeleted = ecriture.dateDeleted ? moment(ecriture.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
