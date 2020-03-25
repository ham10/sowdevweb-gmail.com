import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeLit } from 'app/shared/model/type-lit.model';

type EntityResponseType = HttpResponse<ITypeLit>;
type EntityArrayResponseType = HttpResponse<ITypeLit[]>;

@Injectable({ providedIn: 'root' })
export class TypeLitService {
  public resourceUrl = SERVER_API_URL + 'api/type-lits';

  constructor(protected http: HttpClient) {}

  create(typeLit: ITypeLit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeLit);
    return this.http
      .post<ITypeLit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeLit: ITypeLit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeLit);
    return this.http
      .put<ITypeLit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeLit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeLit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeLit: ITypeLit): ITypeLit {
    const copy: ITypeLit = Object.assign({}, typeLit, {
      dateCreated: typeLit.dateCreated && typeLit.dateCreated.isValid() ? typeLit.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeLit.dateUpdated && typeLit.dateUpdated.isValid() ? typeLit.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeLit: ITypeLit) => {
        typeLit.dateCreated = typeLit.dateCreated ? moment(typeLit.dateCreated) : undefined;
        typeLit.dateUpdated = typeLit.dateUpdated ? moment(typeLit.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
