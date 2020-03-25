import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacture } from 'app/shared/model/facture.model';

type EntityResponseType = HttpResponse<IFacture>;
type EntityArrayResponseType = HttpResponse<IFacture[]>;

@Injectable({ providedIn: 'root' })
export class FactureService {
  public resourceUrl = SERVER_API_URL + 'api/factures';

  constructor(protected http: HttpClient) {}

  create(facture: IFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facture);
    return this.http
      .post<IFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facture: IFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facture);
    return this.http
      .put<IFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facture: IFacture): IFacture {
    const copy: IFacture = Object.assign({}, facture, {
      dateCreated: facture.dateCreated && facture.dateCreated.isValid() ? facture.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: facture.dateUpdated && facture.dateUpdated.isValid() ? facture.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: facture.dateDeleted && facture.dateDeleted.isValid() ? facture.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((facture: IFacture) => {
        facture.dateCreated = facture.dateCreated ? moment(facture.dateCreated) : undefined;
        facture.dateUpdated = facture.dateUpdated ? moment(facture.dateUpdated) : undefined;
        facture.dateDeleted = facture.dateDeleted ? moment(facture.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
