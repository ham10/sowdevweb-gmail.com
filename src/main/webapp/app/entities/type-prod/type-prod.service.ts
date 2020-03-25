import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeProd } from 'app/shared/model/type-prod.model';

type EntityResponseType = HttpResponse<ITypeProd>;
type EntityArrayResponseType = HttpResponse<ITypeProd[]>;

@Injectable({ providedIn: 'root' })
export class TypeProdService {
  public resourceUrl = SERVER_API_URL + 'api/type-prods';

  constructor(protected http: HttpClient) {}

  create(typeProd: ITypeProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeProd);
    return this.http
      .post<ITypeProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeProd: ITypeProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeProd);
    return this.http
      .put<ITypeProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeProd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeProd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeProd: ITypeProd): ITypeProd {
    const copy: ITypeProd = Object.assign({}, typeProd, {
      dateCreated: typeProd.dateCreated && typeProd.dateCreated.isValid() ? typeProd.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeProd.dateUpdated && typeProd.dateUpdated.isValid() ? typeProd.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeProd: ITypeProd) => {
        typeProd.dateCreated = typeProd.dateCreated ? moment(typeProd.dateCreated) : undefined;
        typeProd.dateUpdated = typeProd.dateUpdated ? moment(typeProd.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
