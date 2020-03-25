import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeFournis } from 'app/shared/model/type-fournis.model';

type EntityResponseType = HttpResponse<ITypeFournis>;
type EntityArrayResponseType = HttpResponse<ITypeFournis[]>;

@Injectable({ providedIn: 'root' })
export class TypeFournisService {
  public resourceUrl = SERVER_API_URL + 'api/type-fournis';

  constructor(protected http: HttpClient) {}

  create(typeFournis: ITypeFournis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFournis);
    return this.http
      .post<ITypeFournis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeFournis: ITypeFournis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeFournis);
    return this.http
      .put<ITypeFournis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeFournis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeFournis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeFournis: ITypeFournis): ITypeFournis {
    const copy: ITypeFournis = Object.assign({}, typeFournis, {
      dateCreated: typeFournis.dateCreated && typeFournis.dateCreated.isValid() ? typeFournis.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeFournis.dateUpdated && typeFournis.dateUpdated.isValid() ? typeFournis.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeFournis: ITypeFournis) => {
        typeFournis.dateCreated = typeFournis.dateCreated ? moment(typeFournis.dateCreated) : undefined;
        typeFournis.dateUpdated = typeFournis.dateUpdated ? moment(typeFournis.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
