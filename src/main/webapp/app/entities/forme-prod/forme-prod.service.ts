import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormeProd } from 'app/shared/model/forme-prod.model';

type EntityResponseType = HttpResponse<IFormeProd>;
type EntityArrayResponseType = HttpResponse<IFormeProd[]>;

@Injectable({ providedIn: 'root' })
export class FormeProdService {
  public resourceUrl = SERVER_API_URL + 'api/forme-prods';

  constructor(protected http: HttpClient) {}

  create(formeProd: IFormeProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formeProd);
    return this.http
      .post<IFormeProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formeProd: IFormeProd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formeProd);
    return this.http
      .put<IFormeProd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormeProd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormeProd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(formeProd: IFormeProd): IFormeProd {
    const copy: IFormeProd = Object.assign({}, formeProd, {
      dateCreated: formeProd.dateCreated && formeProd.dateCreated.isValid() ? formeProd.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: formeProd.dateUpdated && formeProd.dateUpdated.isValid() ? formeProd.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((formeProd: IFormeProd) => {
        formeProd.dateCreated = formeProd.dateCreated ? moment(formeProd.dateCreated) : undefined;
        formeProd.dateUpdated = formeProd.dateUpdated ? moment(formeProd.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
