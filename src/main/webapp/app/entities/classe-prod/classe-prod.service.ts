import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClasseProd } from 'app/shared/model/classe-prod.model';

type EntityResponseType = HttpResponse<IClasseProd>;
type EntityArrayResponseType = HttpResponse<IClasseProd[]>;

@Injectable({ providedIn: 'root' })
export class ClasseProdService {
  public resourceUrl = SERVER_API_URL + 'api/classe-prods';

  constructor(protected http: HttpClient) {}

  create(classeProd: IClasseProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classeProd);
    return this.http
      .post<IClasseProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(classeProd: IClasseProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classeProd);
    return this.http
      .put<IClasseProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClasseProd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClasseProd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(classeProd: IClasseProd): IClasseProd {
    const copy: IClasseProd = Object.assign({}, classeProd, {
      dateCreated: classeProd.dateCreated && classeProd.dateCreated.isValid() ? classeProd.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: classeProd.dateUpdated && classeProd.dateUpdated.isValid() ? classeProd.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((classeProd: IClasseProd) => {
        classeProd.dateCreated = classeProd.dateCreated ? moment(classeProd.dateCreated) : undefined;
        classeProd.dateUpdated = classeProd.dateUpdated ? moment(classeProd.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
