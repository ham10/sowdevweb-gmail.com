import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEcheancier } from 'app/shared/model/echeancier.model';

type EntityResponseType = HttpResponse<IEcheancier>;
type EntityArrayResponseType = HttpResponse<IEcheancier[]>;

@Injectable({ providedIn: 'root' })
export class EcheancierService {
  public resourceUrl = SERVER_API_URL + 'api/echeanciers';

  constructor(protected http: HttpClient) {}

  create(echeancier: IEcheancier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(echeancier);
    return this.http
      .post<IEcheancier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(echeancier: IEcheancier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(echeancier);
    return this.http
      .put<IEcheancier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEcheancier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEcheancier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(echeancier: IEcheancier): IEcheancier {
    const copy: IEcheancier = Object.assign({}, echeancier, {
      date: echeancier.date && echeancier.date.isValid() ? echeancier.date.format(DATE_FORMAT) : undefined,
      datePaiement: echeancier.datePaiement && echeancier.datePaiement.isValid() ? echeancier.datePaiement.format(DATE_FORMAT) : undefined,
      dateCreated: echeancier.dateCreated && echeancier.dateCreated.isValid() ? echeancier.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: echeancier.dateUpdated && echeancier.dateUpdated.isValid() ? echeancier.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: echeancier.dateDeleted && echeancier.dateDeleted.isValid() ? echeancier.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.datePaiement = res.body.datePaiement ? moment(res.body.datePaiement) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((echeancier: IEcheancier) => {
        echeancier.date = echeancier.date ? moment(echeancier.date) : undefined;
        echeancier.datePaiement = echeancier.datePaiement ? moment(echeancier.datePaiement) : undefined;
        echeancier.dateCreated = echeancier.dateCreated ? moment(echeancier.dateCreated) : undefined;
        echeancier.dateUpdated = echeancier.dateUpdated ? moment(echeancier.dateUpdated) : undefined;
        echeancier.dateDeleted = echeancier.dateDeleted ? moment(echeancier.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
