import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeChamps } from 'app/shared/model/type-champs.model';

type EntityResponseType = HttpResponse<ITypeChamps>;
type EntityArrayResponseType = HttpResponse<ITypeChamps[]>;

@Injectable({ providedIn: 'root' })
export class TypeChampsService {
  public resourceUrl = SERVER_API_URL + 'api/type-champs';

  constructor(protected http: HttpClient) {}

  create(typeChamps: ITypeChamps): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeChamps);
    return this.http
      .post<ITypeChamps>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeChamps: ITypeChamps): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeChamps);
    return this.http
      .put<ITypeChamps>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeChamps>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeChamps[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeChamps: ITypeChamps): ITypeChamps {
    const copy: ITypeChamps = Object.assign({}, typeChamps, {
      dateCreated: typeChamps.dateCreated && typeChamps.dateCreated.isValid() ? typeChamps.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeChamps.dateUpdated && typeChamps.dateUpdated.isValid() ? typeChamps.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeChamps: ITypeChamps) => {
        typeChamps.dateCreated = typeChamps.dateCreated ? moment(typeChamps.dateCreated) : undefined;
        typeChamps.dateUpdated = typeChamps.dateUpdated ? moment(typeChamps.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
