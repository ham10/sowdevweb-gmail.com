import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

type EntityResponseType = HttpResponse<ITypeMvtStock>;
type EntityArrayResponseType = HttpResponse<ITypeMvtStock[]>;

@Injectable({ providedIn: 'root' })
export class TypeMvtStockService {
  public resourceUrl = SERVER_API_URL + 'api/type-mvt-stocks';

  constructor(protected http: HttpClient) {}

  create(typeMvtStock: ITypeMvtStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeMvtStock);
    return this.http
      .post<ITypeMvtStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeMvtStock: ITypeMvtStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeMvtStock);
    return this.http
      .put<ITypeMvtStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeMvtStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeMvtStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeMvtStock: ITypeMvtStock): ITypeMvtStock {
    const copy: ITypeMvtStock = Object.assign({}, typeMvtStock, {
      dateCreated:
        typeMvtStock.dateCreated && typeMvtStock.dateCreated.isValid() ? typeMvtStock.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeMvtStock.dateUpdated && typeMvtStock.dateUpdated.isValid() ? typeMvtStock.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeMvtStock: ITypeMvtStock) => {
        typeMvtStock.dateCreated = typeMvtStock.dateCreated ? moment(typeMvtStock.dateCreated) : undefined;
        typeMvtStock.dateUpdated = typeMvtStock.dateUpdated ? moment(typeMvtStock.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
