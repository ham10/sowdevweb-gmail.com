import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITabAmortis } from 'app/shared/model/tab-amortis.model';

type EntityResponseType = HttpResponse<ITabAmortis>;
type EntityArrayResponseType = HttpResponse<ITabAmortis[]>;

@Injectable({ providedIn: 'root' })
export class TabAmortisService {
  public resourceUrl = SERVER_API_URL + 'api/tab-amortis';

  constructor(protected http: HttpClient) {}

  create(tabAmortis: ITabAmortis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabAmortis);
    return this.http
      .post<ITabAmortis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tabAmortis: ITabAmortis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabAmortis);
    return this.http
      .put<ITabAmortis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITabAmortis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITabAmortis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tabAmortis: ITabAmortis): ITabAmortis {
    const copy: ITabAmortis = Object.assign({}, tabAmortis, {
      date: tabAmortis.date && tabAmortis.date.isValid() ? tabAmortis.date.format(DATE_FORMAT) : undefined,
      dateCreated: tabAmortis.dateCreated && tabAmortis.dateCreated.isValid() ? tabAmortis.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: tabAmortis.dateUpdated && tabAmortis.dateUpdated.isValid() ? tabAmortis.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: tabAmortis.dateDeleted && tabAmortis.dateDeleted.isValid() ? tabAmortis.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tabAmortis: ITabAmortis) => {
        tabAmortis.date = tabAmortis.date ? moment(tabAmortis.date) : undefined;
        tabAmortis.dateCreated = tabAmortis.dateCreated ? moment(tabAmortis.dateCreated) : undefined;
        tabAmortis.dateUpdated = tabAmortis.dateUpdated ? moment(tabAmortis.dateUpdated) : undefined;
        tabAmortis.dateDeleted = tabAmortis.dateDeleted ? moment(tabAmortis.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
