import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlat } from 'app/shared/model/plat.model';

type EntityResponseType = HttpResponse<IPlat>;
type EntityArrayResponseType = HttpResponse<IPlat[]>;

@Injectable({ providedIn: 'root' })
export class PlatService {
  public resourceUrl = SERVER_API_URL + 'api/plats';

  constructor(protected http: HttpClient) {}

  create(plat: IPlat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plat);
    return this.http
      .post<IPlat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plat: IPlat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plat);
    return this.http
      .put<IPlat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(plat: IPlat): IPlat {
    const copy: IPlat = Object.assign({}, plat, {
      date: plat.date && plat.date.isValid() ? plat.date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((plat: IPlat) => {
        plat.date = plat.date ? moment(plat.date) : undefined;
      });
    }
    return res;
  }
}
