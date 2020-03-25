import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeSoins } from 'app/shared/model/type-soins.model';

type EntityResponseType = HttpResponse<ITypeSoins>;
type EntityArrayResponseType = HttpResponse<ITypeSoins[]>;

@Injectable({ providedIn: 'root' })
export class TypeSoinsService {
  public resourceUrl = SERVER_API_URL + 'api/type-soins';

  constructor(protected http: HttpClient) {}

  create(typeSoins: ITypeSoins): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeSoins);
    return this.http
      .post<ITypeSoins>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeSoins: ITypeSoins): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeSoins);
    return this.http
      .put<ITypeSoins>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeSoins>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeSoins[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeSoins: ITypeSoins): ITypeSoins {
    const copy: ITypeSoins = Object.assign({}, typeSoins, {
      dateCreated: typeSoins.dateCreated && typeSoins.dateCreated.isValid() ? typeSoins.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeSoins.dateUpdated && typeSoins.dateUpdated.isValid() ? typeSoins.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeSoins: ITypeSoins) => {
        typeSoins.dateCreated = typeSoins.dateCreated ? moment(typeSoins.dateCreated) : undefined;
        typeSoins.dateUpdated = typeSoins.dateUpdated ? moment(typeSoins.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
