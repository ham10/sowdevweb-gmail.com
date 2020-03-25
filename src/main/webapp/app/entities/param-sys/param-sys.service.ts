import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParamSys } from 'app/shared/model/param-sys.model';

type EntityResponseType = HttpResponse<IParamSys>;
type EntityArrayResponseType = HttpResponse<IParamSys[]>;

@Injectable({ providedIn: 'root' })
export class ParamSysService {
  public resourceUrl = SERVER_API_URL + 'api/param-sys';

  constructor(protected http: HttpClient) {}

  create(paramSys: IParamSys): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramSys);
    return this.http
      .post<IParamSys>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paramSys: IParamSys): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramSys);
    return this.http
      .put<IParamSys>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParamSys>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParamSys[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paramSys: IParamSys): IParamSys {
    const copy: IParamSys = Object.assign({}, paramSys, {
      dateCreated: paramSys.dateCreated && paramSys.dateCreated.isValid() ? paramSys.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: paramSys.dateUpdated && paramSys.dateUpdated.isValid() ? paramSys.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((paramSys: IParamSys) => {
        paramSys.dateCreated = paramSys.dateCreated ? moment(paramSys.dateCreated) : undefined;
        paramSys.dateUpdated = paramSys.dateUpdated ? moment(paramSys.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
