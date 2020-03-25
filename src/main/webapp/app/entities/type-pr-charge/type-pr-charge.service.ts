import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';

type EntityResponseType = HttpResponse<ITypePrCharge>;
type EntityArrayResponseType = HttpResponse<ITypePrCharge[]>;

@Injectable({ providedIn: 'root' })
export class TypePrChargeService {
  public resourceUrl = SERVER_API_URL + 'api/type-pr-charges';

  constructor(protected http: HttpClient) {}

  create(typePrCharge: ITypePrCharge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePrCharge);
    return this.http
      .post<ITypePrCharge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePrCharge: ITypePrCharge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePrCharge);
    return this.http
      .put<ITypePrCharge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePrCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePrCharge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePrCharge: ITypePrCharge): ITypePrCharge {
    const copy: ITypePrCharge = Object.assign({}, typePrCharge, {
      dateCreated:
        typePrCharge.dateCreated && typePrCharge.dateCreated.isValid() ? typePrCharge.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePrCharge.dateUpdated && typePrCharge.dateUpdated.isValid() ? typePrCharge.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePrCharge: ITypePrCharge) => {
        typePrCharge.dateCreated = typePrCharge.dateCreated ? moment(typePrCharge.dateCreated) : undefined;
        typePrCharge.dateUpdated = typePrCharge.dateUpdated ? moment(typePrCharge.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
