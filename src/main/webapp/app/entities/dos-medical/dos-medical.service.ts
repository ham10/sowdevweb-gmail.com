import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDosMedical } from 'app/shared/model/dos-medical.model';

type EntityResponseType = HttpResponse<IDosMedical>;
type EntityArrayResponseType = HttpResponse<IDosMedical[]>;

@Injectable({ providedIn: 'root' })
export class DosMedicalService {
  public resourceUrl = SERVER_API_URL + 'api/dos-medicals';

  constructor(protected http: HttpClient) {}

  create(dosMedical: IDosMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dosMedical);
    return this.http
      .post<IDosMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dosMedical: IDosMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dosMedical);
    return this.http
      .put<IDosMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDosMedical>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDosMedical[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dosMedical: IDosMedical): IDosMedical {
    const copy: IDosMedical = Object.assign({}, dosMedical, {
      dateCreation: dosMedical.dateCreation && dosMedical.dateCreation.isValid() ? dosMedical.dateCreation.format(DATE_FORMAT) : undefined,
      dateCreated: dosMedical.dateCreated && dosMedical.dateCreated.isValid() ? dosMedical.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: dosMedical.dateUpdated && dosMedical.dateUpdated.isValid() ? dosMedical.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dosMedical: IDosMedical) => {
        dosMedical.dateCreation = dosMedical.dateCreation ? moment(dosMedical.dateCreation) : undefined;
        dosMedical.dateCreated = dosMedical.dateCreated ? moment(dosMedical.dateCreated) : undefined;
        dosMedical.dateUpdated = dosMedical.dateUpdated ? moment(dosMedical.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
