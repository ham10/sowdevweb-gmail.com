import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMachAutorise } from 'app/shared/model/mach-autorise.model';

type EntityResponseType = HttpResponse<IMachAutorise>;
type EntityArrayResponseType = HttpResponse<IMachAutorise[]>;

@Injectable({ providedIn: 'root' })
export class MachAutoriseService {
  public resourceUrl = SERVER_API_URL + 'api/mach-autorises';

  constructor(protected http: HttpClient) {}

  create(machAutorise: IMachAutorise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(machAutorise);
    return this.http
      .post<IMachAutorise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(machAutorise: IMachAutorise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(machAutorise);
    return this.http
      .put<IMachAutorise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMachAutorise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMachAutorise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(machAutorise: IMachAutorise): IMachAutorise {
    const copy: IMachAutorise = Object.assign({}, machAutorise, {
      dateCreated:
        machAutorise.dateCreated && machAutorise.dateCreated.isValid() ? machAutorise.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: machAutorise.dateUpdated && machAutorise.dateUpdated.isValid() ? machAutorise.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((machAutorise: IMachAutorise) => {
        machAutorise.dateCreated = machAutorise.dateCreated ? moment(machAutorise.dateCreated) : undefined;
        machAutorise.dateUpdated = machAutorise.dateUpdated ? moment(machAutorise.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
