import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INatureOp } from 'app/shared/model/nature-op.model';

type EntityResponseType = HttpResponse<INatureOp>;
type EntityArrayResponseType = HttpResponse<INatureOp[]>;

@Injectable({ providedIn: 'root' })
export class NatureOpService {
  public resourceUrl = SERVER_API_URL + 'api/nature-ops';

  constructor(protected http: HttpClient) {}

  create(natureOp: INatureOp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureOp);
    return this.http
      .post<INatureOp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureOp: INatureOp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureOp);
    return this.http
      .put<INatureOp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureOp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureOp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(natureOp: INatureOp): INatureOp {
    const copy: INatureOp = Object.assign({}, natureOp, {
      dateCreated: natureOp.dateCreated && natureOp.dateCreated.isValid() ? natureOp.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: natureOp.dateUpdated && natureOp.dateUpdated.isValid() ? natureOp.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: natureOp.dateDeleted && natureOp.dateDeleted.isValid() ? natureOp.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((natureOp: INatureOp) => {
        natureOp.dateCreated = natureOp.dateCreated ? moment(natureOp.dateCreated) : undefined;
        natureOp.dateUpdated = natureOp.dateUpdated ? moment(natureOp.dateUpdated) : undefined;
        natureOp.dateDeleted = natureOp.dateDeleted ? moment(natureOp.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
