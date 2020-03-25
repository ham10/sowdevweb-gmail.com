import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItem } from 'app/shared/model/item.model';

type EntityResponseType = HttpResponse<IItem>;
type EntityArrayResponseType = HttpResponse<IItem[]>;

@Injectable({ providedIn: 'root' })
export class ItemService {
  public resourceUrl = SERVER_API_URL + 'api/items';

  constructor(protected http: HttpClient) {}

  create(item: IItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(item);
    return this.http
      .post<IItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(item: IItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(item);
    return this.http
      .put<IItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(item: IItem): IItem {
    const copy: IItem = Object.assign({}, item, {
      dateCreated: item.dateCreated && item.dateCreated.isValid() ? item.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: item.dateUpdated && item.dateUpdated.isValid() ? item.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((item: IItem) => {
        item.dateCreated = item.dateCreated ? moment(item.dateCreated) : undefined;
        item.dateUpdated = item.dateUpdated ? moment(item.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
