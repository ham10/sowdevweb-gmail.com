import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAntecedent } from 'app/shared/model/antecedent.model';

type EntityResponseType = HttpResponse<IAntecedent>;
type EntityArrayResponseType = HttpResponse<IAntecedent[]>;

@Injectable({ providedIn: 'root' })
export class AntecedentService {
  public resourceUrl = SERVER_API_URL + 'api/antecedents';

  constructor(protected http: HttpClient) {}

  create(antecedent: IAntecedent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(antecedent);
    return this.http
      .post<IAntecedent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(antecedent: IAntecedent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(antecedent);
    return this.http
      .put<IAntecedent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAntecedent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAntecedent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(antecedent: IAntecedent): IAntecedent {
    const copy: IAntecedent = Object.assign({}, antecedent, {
      date: antecedent.date && antecedent.date.isValid() ? antecedent.date.format(DATE_FORMAT) : undefined,
      dateCreated: antecedent.dateCreated && antecedent.dateCreated.isValid() ? antecedent.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: antecedent.dateUpdated && antecedent.dateUpdated.isValid() ? antecedent.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((antecedent: IAntecedent) => {
        antecedent.date = antecedent.date ? moment(antecedent.date) : undefined;
        antecedent.dateCreated = antecedent.dateCreated ? moment(antecedent.dateCreated) : undefined;
        antecedent.dateUpdated = antecedent.dateUpdated ? moment(antecedent.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
