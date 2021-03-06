import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePatient } from 'app/shared/model/type-patient.model';

type EntityResponseType = HttpResponse<ITypePatient>;
type EntityArrayResponseType = HttpResponse<ITypePatient[]>;

@Injectable({ providedIn: 'root' })
export class TypePatientService {
  public resourceUrl = SERVER_API_URL + 'api/type-patients';

  constructor(protected http: HttpClient) {}

  create(typePatient: ITypePatient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePatient);
    return this.http
      .post<ITypePatient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePatient: ITypePatient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePatient);
    return this.http
      .put<ITypePatient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePatient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePatient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePatient: ITypePatient): ITypePatient {
    const copy: ITypePatient = Object.assign({}, typePatient, {
      dateCreated: typePatient.dateCreated && typePatient.dateCreated.isValid() ? typePatient.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePatient.dateUpdated && typePatient.dateUpdated.isValid() ? typePatient.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePatient: ITypePatient) => {
        typePatient.dateCreated = typePatient.dateCreated ? moment(typePatient.dateCreated) : undefined;
        typePatient.dateUpdated = typePatient.dateUpdated ? moment(typePatient.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
