import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeNotif } from 'app/shared/model/type-notif.model';

type EntityResponseType = HttpResponse<ITypeNotif>;
type EntityArrayResponseType = HttpResponse<ITypeNotif[]>;

@Injectable({ providedIn: 'root' })
export class TypeNotifService {
  public resourceUrl = SERVER_API_URL + 'api/type-notifs';

  constructor(protected http: HttpClient) {}

  create(typeNotif: ITypeNotif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeNotif);
    return this.http
      .post<ITypeNotif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeNotif: ITypeNotif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeNotif);
    return this.http
      .put<ITypeNotif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeNotif>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeNotif[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeNotif: ITypeNotif): ITypeNotif {
    const copy: ITypeNotif = Object.assign({}, typeNotif, {
      dateCreated: typeNotif.dateCreated && typeNotif.dateCreated.isValid() ? typeNotif.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeNotif.dateUpdated && typeNotif.dateUpdated.isValid() ? typeNotif.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeNotif: ITypeNotif) => {
        typeNotif.dateCreated = typeNotif.dateCreated ? moment(typeNotif.dateCreated) : undefined;
        typeNotif.dateUpdated = typeNotif.dateUpdated ? moment(typeNotif.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
