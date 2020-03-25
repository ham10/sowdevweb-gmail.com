import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVaccin } from 'app/shared/model/vaccin.model';

type EntityResponseType = HttpResponse<IVaccin>;
type EntityArrayResponseType = HttpResponse<IVaccin[]>;

@Injectable({ providedIn: 'root' })
export class VaccinService {
  public resourceUrl = SERVER_API_URL + 'api/vaccins';

  constructor(protected http: HttpClient) {}

  create(vaccin: IVaccin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vaccin);
    return this.http
      .post<IVaccin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vaccin: IVaccin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vaccin);
    return this.http
      .put<IVaccin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVaccin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVaccin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vaccin: IVaccin): IVaccin {
    const copy: IVaccin = Object.assign({}, vaccin, {
      date: vaccin.date && vaccin.date.isValid() ? vaccin.date.format(DATE_FORMAT) : undefined,
      dateRenouv: vaccin.dateRenouv && vaccin.dateRenouv.isValid() ? vaccin.dateRenouv.format(DATE_FORMAT) : undefined,
      dateCreated: vaccin.dateCreated && vaccin.dateCreated.isValid() ? vaccin.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: vaccin.dateUpdated && vaccin.dateUpdated.isValid() ? vaccin.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateRenouv = res.body.dateRenouv ? moment(res.body.dateRenouv) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vaccin: IVaccin) => {
        vaccin.date = vaccin.date ? moment(vaccin.date) : undefined;
        vaccin.dateRenouv = vaccin.dateRenouv ? moment(vaccin.dateRenouv) : undefined;
        vaccin.dateCreated = vaccin.dateCreated ? moment(vaccin.dateCreated) : undefined;
        vaccin.dateUpdated = vaccin.dateUpdated ? moment(vaccin.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
