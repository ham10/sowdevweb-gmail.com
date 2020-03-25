import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeImmo } from 'app/shared/model/type-immo.model';

type EntityResponseType = HttpResponse<ITypeImmo>;
type EntityArrayResponseType = HttpResponse<ITypeImmo[]>;

@Injectable({ providedIn: 'root' })
export class TypeImmoService {
  public resourceUrl = SERVER_API_URL + 'api/type-immos';

  constructor(protected http: HttpClient) {}

  create(typeImmo: ITypeImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeImmo);
    return this.http
      .post<ITypeImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeImmo: ITypeImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeImmo);
    return this.http
      .put<ITypeImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeImmo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeImmo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeImmo: ITypeImmo): ITypeImmo {
    const copy: ITypeImmo = Object.assign({}, typeImmo, {
      dateCreated: typeImmo.dateCreated && typeImmo.dateCreated.isValid() ? typeImmo.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeImmo.dateUpdated && typeImmo.dateUpdated.isValid() ? typeImmo.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: typeImmo.dateDeleted && typeImmo.dateDeleted.isValid() ? typeImmo.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeImmo: ITypeImmo) => {
        typeImmo.dateCreated = typeImmo.dateCreated ? moment(typeImmo.dateCreated) : undefined;
        typeImmo.dateUpdated = typeImmo.dateUpdated ? moment(typeImmo.dateUpdated) : undefined;
        typeImmo.dateDeleted = typeImmo.dateDeleted ? moment(typeImmo.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
