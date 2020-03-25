import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModeRegle } from 'app/shared/model/mode-regle.model';

type EntityResponseType = HttpResponse<IModeRegle>;
type EntityArrayResponseType = HttpResponse<IModeRegle[]>;

@Injectable({ providedIn: 'root' })
export class ModeRegleService {
  public resourceUrl = SERVER_API_URL + 'api/mode-regles';

  constructor(protected http: HttpClient) {}

  create(modeRegle: IModeRegle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeRegle);
    return this.http
      .post<IModeRegle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(modeRegle: IModeRegle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modeRegle);
    return this.http
      .put<IModeRegle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModeRegle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModeRegle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(modeRegle: IModeRegle): IModeRegle {
    const copy: IModeRegle = Object.assign({}, modeRegle, {
      dateCreated: modeRegle.dateCreated && modeRegle.dateCreated.isValid() ? modeRegle.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: modeRegle.dateUpdated && modeRegle.dateUpdated.isValid() ? modeRegle.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((modeRegle: IModeRegle) => {
        modeRegle.dateCreated = modeRegle.dateCreated ? moment(modeRegle.dateCreated) : undefined;
        modeRegle.dateUpdated = modeRegle.dateUpdated ? moment(modeRegle.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
