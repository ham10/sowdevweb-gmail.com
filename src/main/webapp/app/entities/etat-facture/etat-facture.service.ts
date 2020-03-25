import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatFacture } from 'app/shared/model/etat-facture.model';

type EntityResponseType = HttpResponse<IEtatFacture>;
type EntityArrayResponseType = HttpResponse<IEtatFacture[]>;

@Injectable({ providedIn: 'root' })
export class EtatFactureService {
  public resourceUrl = SERVER_API_URL + 'api/etat-factures';

  constructor(protected http: HttpClient) {}

  create(etatFacture: IEtatFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatFacture);
    return this.http
      .post<IEtatFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etatFacture: IEtatFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatFacture);
    return this.http
      .put<IEtatFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtatFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatFacture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etatFacture: IEtatFacture): IEtatFacture {
    const copy: IEtatFacture = Object.assign({}, etatFacture, {
      dateCreated: etatFacture.dateCreated && etatFacture.dateCreated.isValid() ? etatFacture.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: etatFacture.dateUpdated && etatFacture.dateUpdated.isValid() ? etatFacture.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: etatFacture.dateDeleted && etatFacture.dateDeleted.isValid() ? etatFacture.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((etatFacture: IEtatFacture) => {
        etatFacture.dateCreated = etatFacture.dateCreated ? moment(etatFacture.dateCreated) : undefined;
        etatFacture.dateUpdated = etatFacture.dateUpdated ? moment(etatFacture.dateUpdated) : undefined;
        etatFacture.dateDeleted = etatFacture.dateDeleted ? moment(etatFacture.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
