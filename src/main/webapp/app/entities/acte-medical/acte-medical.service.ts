import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActeMedical } from 'app/shared/model/acte-medical.model';

type EntityResponseType = HttpResponse<IActeMedical>;
type EntityArrayResponseType = HttpResponse<IActeMedical[]>;

@Injectable({ providedIn: 'root' })
export class ActeMedicalService {
  public resourceUrl = SERVER_API_URL + 'api/acte-medicals';

  constructor(protected http: HttpClient) {}

  create(acteMedical: IActeMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acteMedical);
    return this.http
      .post<IActeMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(acteMedical: IActeMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acteMedical);
    return this.http
      .put<IActeMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActeMedical>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActeMedical[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(acteMedical: IActeMedical): IActeMedical {
    const copy: IActeMedical = Object.assign({}, acteMedical, {
      dateCreated: acteMedical.dateCreated && acteMedical.dateCreated.isValid() ? acteMedical.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: acteMedical.dateUpdated && acteMedical.dateUpdated.isValid() ? acteMedical.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((acteMedical: IActeMedical) => {
        acteMedical.dateCreated = acteMedical.dateCreated ? moment(acteMedical.dateCreated) : undefined;
        acteMedical.dateUpdated = acteMedical.dateUpdated ? moment(acteMedical.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
