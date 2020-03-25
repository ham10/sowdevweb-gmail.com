import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeFacture } from 'app/shared/model/type-facture.model';

type EntityResponseType = HttpResponse<ITypeFacture>;
type EntityArrayResponseType = HttpResponse<ITypeFacture[]>;

@Injectable({ providedIn: 'root' })
export class TypeFactureService {
  public resourceUrl = SERVER_API_URL + 'api/type-factures';

  constructor(protected http: HttpClient) {}

  create(typeFacture: ITypeFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFacture);
    return this.http
      .post<ITypeFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeFacture: ITypeFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFacture);
    return this.http
      .put<ITypeFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeFacture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeFacture: ITypeFacture): ITypeFacture {
    const copy: ITypeFacture = Object.assign({}, typeFacture, {
      dateCreated: typeFacture.dateCreated && typeFacture.dateCreated.isValid() ? typeFacture.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeFacture.dateUpdated && typeFacture.dateUpdated.isValid() ? typeFacture.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeFacture: ITypeFacture) => {
        typeFacture.dateCreated = typeFacture.dateCreated ? moment(typeFacture.dateCreated) : undefined;
        typeFacture.dateUpdated = typeFacture.dateUpdated ? moment(typeFacture.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
