import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeUnite } from 'app/shared/model/type-unite.model';

type EntityResponseType = HttpResponse<ITypeUnite>;
type EntityArrayResponseType = HttpResponse<ITypeUnite[]>;

@Injectable({ providedIn: 'root' })
export class TypeUniteService {
  public resourceUrl = SERVER_API_URL + 'api/type-unites';

  constructor(protected http: HttpClient) {}

  create(typeUnite: ITypeUnite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeUnite);
    return this.http
      .post<ITypeUnite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeUnite: ITypeUnite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeUnite);
    return this.http
      .put<ITypeUnite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeUnite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeUnite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeUnite: ITypeUnite): ITypeUnite {
    const copy: ITypeUnite = Object.assign({}, typeUnite, {
      dateCreated: typeUnite.dateCreated && typeUnite.dateCreated.isValid() ? typeUnite.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeUnite.dateUpdated && typeUnite.dateUpdated.isValid() ? typeUnite.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeUnite: ITypeUnite) => {
        typeUnite.dateCreated = typeUnite.dateCreated ? moment(typeUnite.dateCreated) : undefined;
        typeUnite.dateUpdated = typeUnite.dateUpdated ? moment(typeUnite.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
