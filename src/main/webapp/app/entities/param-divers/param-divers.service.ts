import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParamDivers } from 'app/shared/model/param-divers.model';

type EntityResponseType = HttpResponse<IParamDivers>;
type EntityArrayResponseType = HttpResponse<IParamDivers[]>;

@Injectable({ providedIn: 'root' })
export class ParamDiversService {
  public resourceUrl = SERVER_API_URL + 'api/param-divers';

  constructor(protected http: HttpClient) {}

  create(paramDivers: IParamDivers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramDivers);
    return this.http
      .post<IParamDivers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paramDivers: IParamDivers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paramDivers);
    return this.http
      .put<IParamDivers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParamDivers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParamDivers[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paramDivers: IParamDivers): IParamDivers {
    const copy: IParamDivers = Object.assign({}, paramDivers, {
      dateCreated: paramDivers.dateCreated && paramDivers.dateCreated.isValid() ? paramDivers.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: paramDivers.dateUpdated && paramDivers.dateUpdated.isValid() ? paramDivers.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((paramDivers: IParamDivers) => {
        paramDivers.dateCreated = paramDivers.dateCreated ? moment(paramDivers.dateCreated) : undefined;
        paramDivers.dateUpdated = paramDivers.dateUpdated ? moment(paramDivers.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
