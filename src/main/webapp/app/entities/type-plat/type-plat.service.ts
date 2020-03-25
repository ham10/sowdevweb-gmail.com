import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePlat } from 'app/shared/model/type-plat.model';

type EntityResponseType = HttpResponse<ITypePlat>;
type EntityArrayResponseType = HttpResponse<ITypePlat[]>;

@Injectable({ providedIn: 'root' })
export class TypePlatService {
  public resourceUrl = SERVER_API_URL + 'api/type-plats';

  constructor(protected http: HttpClient) {}

  create(typePlat: ITypePlat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePlat);
    return this.http
      .post<ITypePlat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePlat: ITypePlat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePlat);
    return this.http
      .put<ITypePlat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePlat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePlat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePlat: ITypePlat): ITypePlat {
    const copy: ITypePlat = Object.assign({}, typePlat, {
      dateCreated: typePlat.dateCreated && typePlat.dateCreated.isValid() ? typePlat.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePlat.dateUpdated && typePlat.dateUpdated.isValid() ? typePlat.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePlat: ITypePlat) => {
        typePlat.dateCreated = typePlat.dateCreated ? moment(typePlat.dateCreated) : undefined;
        typePlat.dateUpdated = typePlat.dateUpdated ? moment(typePlat.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
