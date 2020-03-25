import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtablis } from 'app/shared/model/etablis.model';

type EntityResponseType = HttpResponse<IEtablis>;
type EntityArrayResponseType = HttpResponse<IEtablis[]>;

@Injectable({ providedIn: 'root' })
export class EtablisService {
  public resourceUrl = SERVER_API_URL + 'api/etablis';

  constructor(protected http: HttpClient) {}

  create(etablis: IEtablis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etablis);
    return this.http
      .post<IEtablis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etablis: IEtablis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etablis);
    return this.http
      .put<IEtablis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtablis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtablis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etablis: IEtablis): IEtablis {
    const copy: IEtablis = Object.assign({}, etablis, {
      dateCreated: etablis.dateCreated && etablis.dateCreated.isValid() ? etablis.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: etablis.dateUpdated && etablis.dateUpdated.isValid() ? etablis.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((etablis: IEtablis) => {
        etablis.dateCreated = etablis.dateCreated ? moment(etablis.dateCreated) : undefined;
        etablis.dateUpdated = etablis.dateUpdated ? moment(etablis.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
