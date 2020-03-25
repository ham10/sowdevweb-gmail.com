import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeConstante } from 'app/shared/model/type-constante.model';

type EntityResponseType = HttpResponse<ITypeConstante>;
type EntityArrayResponseType = HttpResponse<ITypeConstante[]>;

@Injectable({ providedIn: 'root' })
export class TypeConstanteService {
  public resourceUrl = SERVER_API_URL + 'api/type-constantes';

  constructor(protected http: HttpClient) {}

  create(typeConstante: ITypeConstante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeConstante);
    return this.http
      .post<ITypeConstante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeConstante: ITypeConstante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeConstante);
    return this.http
      .put<ITypeConstante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeConstante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeConstante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeConstante: ITypeConstante): ITypeConstante {
    const copy: ITypeConstante = Object.assign({}, typeConstante, {
      dateCreated:
        typeConstante.dateCreated && typeConstante.dateCreated.isValid() ? typeConstante.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        typeConstante.dateUpdated && typeConstante.dateUpdated.isValid() ? typeConstante.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeConstante: ITypeConstante) => {
        typeConstante.dateCreated = typeConstante.dateCreated ? moment(typeConstante.dateCreated) : undefined;
        typeConstante.dateUpdated = typeConstante.dateUpdated ? moment(typeConstante.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
