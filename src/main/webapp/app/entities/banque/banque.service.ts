import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBanque } from 'app/shared/model/banque.model';

type EntityResponseType = HttpResponse<IBanque>;
type EntityArrayResponseType = HttpResponse<IBanque[]>;

@Injectable({ providedIn: 'root' })
export class BanqueService {
  public resourceUrl = SERVER_API_URL + 'api/banques';

  constructor(protected http: HttpClient) {}

  create(banque: IBanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(banque);
    return this.http
      .post<IBanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(banque: IBanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(banque);
    return this.http
      .put<IBanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBanque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBanque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(banque: IBanque): IBanque {
    const copy: IBanque = Object.assign({}, banque, {
      dateCreated: banque.dateCreated && banque.dateCreated.isValid() ? banque.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: banque.dateUpdated && banque.dateUpdated.isValid() ? banque.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((banque: IBanque) => {
        banque.dateCreated = banque.dateCreated ? moment(banque.dateCreated) : undefined;
        banque.dateUpdated = banque.dateUpdated ? moment(banque.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
