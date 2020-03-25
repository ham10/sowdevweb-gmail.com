import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPole } from 'app/shared/model/pole.model';

type EntityResponseType = HttpResponse<IPole>;
type EntityArrayResponseType = HttpResponse<IPole[]>;

@Injectable({ providedIn: 'root' })
export class PoleService {
  public resourceUrl = SERVER_API_URL + 'api/poles';

  constructor(protected http: HttpClient) {}

  create(pole: IPole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pole);
    return this.http
      .post<IPole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pole: IPole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pole);
    return this.http
      .put<IPole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pole: IPole): IPole {
    const copy: IPole = Object.assign({}, pole, {
      dateCreated: pole.dateCreated && pole.dateCreated.isValid() ? pole.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: pole.dateUpdated && pole.dateUpdated.isValid() ? pole.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((pole: IPole) => {
        pole.dateCreated = pole.dateCreated ? moment(pole.dateCreated) : undefined;
        pole.dateUpdated = pole.dateUpdated ? moment(pole.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
