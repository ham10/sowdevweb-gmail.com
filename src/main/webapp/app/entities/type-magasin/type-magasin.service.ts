import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeMagasin } from 'app/shared/model/type-magasin.model';

type EntityResponseType = HttpResponse<ITypeMagasin>;
type EntityArrayResponseType = HttpResponse<ITypeMagasin[]>;

@Injectable({ providedIn: 'root' })
export class TypeMagasinService {
  public resourceUrl = SERVER_API_URL + 'api/type-magasins';

  constructor(protected http: HttpClient) {}

  create(typeMagasin: ITypeMagasin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeMagasin);
    return this.http
      .post<ITypeMagasin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeMagasin: ITypeMagasin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeMagasin);
    return this.http
      .put<ITypeMagasin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeMagasin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeMagasin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeMagasin: ITypeMagasin): ITypeMagasin {
    const copy: ITypeMagasin = Object.assign({}, typeMagasin, {
      dateCreated: typeMagasin.dateCreated && typeMagasin.dateCreated.isValid() ? typeMagasin.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeMagasin.dateUpdated && typeMagasin.dateUpdated.isValid() ? typeMagasin.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeMagasin: ITypeMagasin) => {
        typeMagasin.dateCreated = typeMagasin.dateCreated ? moment(typeMagasin.dateCreated) : undefined;
        typeMagasin.dateUpdated = typeMagasin.dateUpdated ? moment(typeMagasin.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
