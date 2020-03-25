import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParamCode } from 'app/shared/model/param-code.model';

type EntityResponseType = HttpResponse<IParamCode>;
type EntityArrayResponseType = HttpResponse<IParamCode[]>;

@Injectable({ providedIn: 'root' })
export class ParamCodeService {
  public resourceUrl = SERVER_API_URL + 'api/param-codes';

  constructor(protected http: HttpClient) {}

  create(paramCode: IParamCode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramCode);
    return this.http
      .post<IParamCode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paramCode: IParamCode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramCode);
    return this.http
      .put<IParamCode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParamCode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParamCode[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paramCode: IParamCode): IParamCode {
    const copy: IParamCode = Object.assign({}, paramCode, {
      dateCreated: paramCode.dateCreated && paramCode.dateCreated.isValid() ? paramCode.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: paramCode.dateUpdated && paramCode.dateUpdated.isValid() ? paramCode.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((paramCode: IParamCode) => {
        paramCode.dateCreated = paramCode.dateCreated ? moment(paramCode.dateCreated) : undefined;
        paramCode.dateUpdated = paramCode.dateUpdated ? moment(paramCode.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
