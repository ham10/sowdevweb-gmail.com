import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRDV } from 'app/shared/model/rdv.model';

type EntityResponseType = HttpResponse<IRDV>;
type EntityArrayResponseType = HttpResponse<IRDV[]>;

@Injectable({ providedIn: 'root' })
export class RDVService {
  public resourceUrl = SERVER_API_URL + 'api/rdvs';

  constructor(protected http: HttpClient) {}

  create(rDV: IRDV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rDV);
    return this.http
      .post<IRDV>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rDV: IRDV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rDV);
    return this.http
      .put<IRDV>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRDV>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRDV[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(rDV: IRDV): IRDV {
    const copy: IRDV = Object.assign({}, rDV, {
      dateRdv: rDV.dateRdv && rDV.dateRdv.isValid() ? rDV.dateRdv.format(DATE_FORMAT) : undefined,
      heureRdv: rDV.heureRdv && rDV.heureRdv.isValid() ? rDV.heureRdv.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateRdv = res.body.dateRdv ? moment(res.body.dateRdv) : undefined;
      res.body.heureRdv = res.body.heureRdv ? moment(res.body.heureRdv) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rDV: IRDV) => {
        rDV.dateRdv = rDV.dateRdv ? moment(rDV.dateRdv) : undefined;
        rDV.heureRdv = rDV.heureRdv ? moment(rDV.heureRdv) : undefined;
      });
    }
    return res;
  }
}
