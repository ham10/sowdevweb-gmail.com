import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPays } from 'app/shared/model/pays.model';

type EntityResponseType = HttpResponse<IPays>;
type EntityArrayResponseType = HttpResponse<IPays[]>;

@Injectable({ providedIn: 'root' })
export class PaysService {
  public resourceUrl = SERVER_API_URL + 'api/pays';

  constructor(protected http: HttpClient) {}

  create(pays: IPays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pays);
    return this.http
      .post<IPays>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pays: IPays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pays);
    return this.http
      .put<IPays>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPays>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPays[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pays: IPays): IPays {
    const copy: IPays = Object.assign({}, pays, {
      dateCreated: pays.dateCreated && pays.dateCreated.isValid() ? pays.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: pays.dateUpdated && pays.dateUpdated.isValid() ? pays.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((pays: IPays) => {
        pays.dateCreated = pays.dateCreated ? moment(pays.dateCreated) : undefined;
        pays.dateUpdated = pays.dateUpdated ? moment(pays.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
