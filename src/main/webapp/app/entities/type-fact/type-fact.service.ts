import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeFact } from 'app/shared/model/type-fact.model';

type EntityResponseType = HttpResponse<ITypeFact>;
type EntityArrayResponseType = HttpResponse<ITypeFact[]>;

@Injectable({ providedIn: 'root' })
export class TypeFactService {
  public resourceUrl = SERVER_API_URL + 'api/type-facts';

  constructor(protected http: HttpClient) {}

  create(typeFact: ITypeFact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFact);
    return this.http
      .post<ITypeFact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeFact: ITypeFact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFact);
    return this.http
      .put<ITypeFact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeFact>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeFact[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeFact: ITypeFact): ITypeFact {
    const copy: ITypeFact = Object.assign({}, typeFact, {
      dateCreated: typeFact.dateCreated && typeFact.dateCreated.isValid() ? typeFact.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeFact.dateUpdated && typeFact.dateUpdated.isValid() ? typeFact.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeFact: ITypeFact) => {
        typeFact.dateCreated = typeFact.dateCreated ? moment(typeFact.dateCreated) : undefined;
        typeFact.dateUpdated = typeFact.dateUpdated ? moment(typeFact.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
