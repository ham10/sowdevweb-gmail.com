import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUnite } from 'app/shared/model/unite.model';

type EntityResponseType = HttpResponse<IUnite>;
type EntityArrayResponseType = HttpResponse<IUnite[]>;

@Injectable({ providedIn: 'root' })
export class UniteService {
  public resourceUrl = SERVER_API_URL + 'api/unites';

  constructor(protected http: HttpClient) {}

  create(unite: IUnite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unite);
    return this.http
      .post<IUnite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(unite: IUnite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unite);
    return this.http
      .put<IUnite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUnite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUnite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(unite: IUnite): IUnite {
    const copy: IUnite = Object.assign({}, unite, {
      dateCreated: unite.dateCreated && unite.dateCreated.isValid() ? unite.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: unite.dateUpdated && unite.dateUpdated.isValid() ? unite.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((unite: IUnite) => {
        unite.dateCreated = unite.dateCreated ? moment(unite.dateCreated) : undefined;
        unite.dateUpdated = unite.dateUpdated ? moment(unite.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
