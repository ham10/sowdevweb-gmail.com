import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePole } from 'app/shared/model/type-pole.model';

type EntityResponseType = HttpResponse<ITypePole>;
type EntityArrayResponseType = HttpResponse<ITypePole[]>;

@Injectable({ providedIn: 'root' })
export class TypePoleService {
  public resourceUrl = SERVER_API_URL + 'api/type-poles';

  constructor(protected http: HttpClient) {}

  create(typePole: ITypePole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePole);
    return this.http
      .post<ITypePole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePole: ITypePole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePole);
    return this.http
      .put<ITypePole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePole: ITypePole): ITypePole {
    const copy: ITypePole = Object.assign({}, typePole, {
      dateCreated: typePole.dateCreated && typePole.dateCreated.isValid() ? typePole.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePole.dateUpdated && typePole.dateUpdated.isValid() ? typePole.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePole: ITypePole) => {
        typePole.dateCreated = typePole.dateCreated ? moment(typePole.dateCreated) : undefined;
        typePole.dateUpdated = typePole.dateUpdated ? moment(typePole.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
