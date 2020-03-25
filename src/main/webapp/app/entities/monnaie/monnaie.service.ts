import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMonnaie } from 'app/shared/model/monnaie.model';

type EntityResponseType = HttpResponse<IMonnaie>;
type EntityArrayResponseType = HttpResponse<IMonnaie[]>;

@Injectable({ providedIn: 'root' })
export class MonnaieService {
  public resourceUrl = SERVER_API_URL + 'api/monnaies';

  constructor(protected http: HttpClient) {}

  create(monnaie: IMonnaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monnaie);
    return this.http
      .post<IMonnaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(monnaie: IMonnaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monnaie);
    return this.http
      .put<IMonnaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMonnaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMonnaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(monnaie: IMonnaie): IMonnaie {
    const copy: IMonnaie = Object.assign({}, monnaie, {
      dateCreated: monnaie.dateCreated && monnaie.dateCreated.isValid() ? monnaie.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: monnaie.dateUpdated && monnaie.dateUpdated.isValid() ? monnaie.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((monnaie: IMonnaie) => {
        monnaie.dateCreated = monnaie.dateCreated ? moment(monnaie.dateCreated) : undefined;
        monnaie.dateUpdated = monnaie.dateUpdated ? moment(monnaie.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
