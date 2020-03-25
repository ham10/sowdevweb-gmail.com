import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDevise } from 'app/shared/model/devise.model';

type EntityResponseType = HttpResponse<IDevise>;
type EntityArrayResponseType = HttpResponse<IDevise[]>;

@Injectable({ providedIn: 'root' })
export class DeviseService {
  public resourceUrl = SERVER_API_URL + 'api/devises';

  constructor(protected http: HttpClient) {}

  create(devise: IDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devise);
    return this.http
      .post<IDevise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(devise: IDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devise);
    return this.http
      .put<IDevise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDevise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDevise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(devise: IDevise): IDevise {
    const copy: IDevise = Object.assign({}, devise, {
      dateCreated: devise.dateCreated && devise.dateCreated.isValid() ? devise.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: devise.dateUpdated && devise.dateUpdated.isValid() ? devise.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((devise: IDevise) => {
        devise.dateCreated = devise.dateCreated ? moment(devise.dateCreated) : undefined;
        devise.dateUpdated = devise.dateUpdated ? moment(devise.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
