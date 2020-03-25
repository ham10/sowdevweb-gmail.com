import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeAntecedent } from 'app/shared/model/type-antecedent.model';

type EntityResponseType = HttpResponse<ITypeAntecedent>;
type EntityArrayResponseType = HttpResponse<ITypeAntecedent[]>;

@Injectable({ providedIn: 'root' })
export class TypeAntecedentService {
  public resourceUrl = SERVER_API_URL + 'api/type-antecedents';

  constructor(protected http: HttpClient) {}

  create(typeAntecedent: ITypeAntecedent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeAntecedent);
    return this.http
      .post<ITypeAntecedent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeAntecedent: ITypeAntecedent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeAntecedent);
    return this.http
      .put<ITypeAntecedent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeAntecedent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeAntecedent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeAntecedent: ITypeAntecedent): ITypeAntecedent {
    const copy: ITypeAntecedent = Object.assign({}, typeAntecedent, {
      dateCreated:
        typeAntecedent.dateCreated && typeAntecedent.dateCreated.isValid() ? typeAntecedent.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        typeAntecedent.dateUpdated && typeAntecedent.dateUpdated.isValid() ? typeAntecedent.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeAntecedent: ITypeAntecedent) => {
        typeAntecedent.dateCreated = typeAntecedent.dateCreated ? moment(typeAntecedent.dateCreated) : undefined;
        typeAntecedent.dateUpdated = typeAntecedent.dateUpdated ? moment(typeAntecedent.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
