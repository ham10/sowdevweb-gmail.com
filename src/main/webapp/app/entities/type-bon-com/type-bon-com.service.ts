import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';

type EntityResponseType = HttpResponse<ITypeBonCom>;
type EntityArrayResponseType = HttpResponse<ITypeBonCom[]>;

@Injectable({ providedIn: 'root' })
export class TypeBonComService {
  public resourceUrl = SERVER_API_URL + 'api/type-bon-coms';

  constructor(protected http: HttpClient) {}

  create(typeBonCom: ITypeBonCom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeBonCom);
    return this.http
      .post<ITypeBonCom>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeBonCom: ITypeBonCom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeBonCom);
    return this.http
      .put<ITypeBonCom>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeBonCom>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeBonCom[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeBonCom: ITypeBonCom): ITypeBonCom {
    const copy: ITypeBonCom = Object.assign({}, typeBonCom, {
      dateCreated: typeBonCom.dateCreated && typeBonCom.dateCreated.isValid() ? typeBonCom.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeBonCom.dateUpdated && typeBonCom.dateUpdated.isValid() ? typeBonCom.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeBonCom: ITypeBonCom) => {
        typeBonCom.dateCreated = typeBonCom.dateCreated ? moment(typeBonCom.dateCreated) : undefined;
        typeBonCom.dateUpdated = typeBonCom.dateUpdated ? moment(typeBonCom.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
