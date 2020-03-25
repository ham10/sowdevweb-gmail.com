import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeDoc } from 'app/shared/model/type-doc.model';

type EntityResponseType = HttpResponse<ITypeDoc>;
type EntityArrayResponseType = HttpResponse<ITypeDoc[]>;

@Injectable({ providedIn: 'root' })
export class TypeDocService {
  public resourceUrl = SERVER_API_URL + 'api/type-docs';

  constructor(protected http: HttpClient) {}

  create(typeDoc: ITypeDoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeDoc);
    return this.http
      .post<ITypeDoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeDoc: ITypeDoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeDoc);
    return this.http
      .put<ITypeDoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeDoc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeDoc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeDoc: ITypeDoc): ITypeDoc {
    const copy: ITypeDoc = Object.assign({}, typeDoc, {
      dateCreated: typeDoc.dateCreated && typeDoc.dateCreated.isValid() ? typeDoc.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeDoc.dateUpdated && typeDoc.dateUpdated.isValid() ? typeDoc.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeDoc: ITypeDoc) => {
        typeDoc.dateCreated = typeDoc.dateCreated ? moment(typeDoc.dateCreated) : undefined;
        typeDoc.dateUpdated = typeDoc.dateUpdated ? moment(typeDoc.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
