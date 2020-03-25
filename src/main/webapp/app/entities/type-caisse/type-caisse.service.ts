import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeCaisse } from 'app/shared/model/type-caisse.model';

type EntityResponseType = HttpResponse<ITypeCaisse>;
type EntityArrayResponseType = HttpResponse<ITypeCaisse[]>;

@Injectable({ providedIn: 'root' })
export class TypeCaisseService {
  public resourceUrl = SERVER_API_URL + 'api/type-caisses';

  constructor(protected http: HttpClient) {}

  create(typeCaisse: ITypeCaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeCaisse);
    return this.http
      .post<ITypeCaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeCaisse: ITypeCaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeCaisse);
    return this.http
      .put<ITypeCaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeCaisse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeCaisse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeCaisse: ITypeCaisse): ITypeCaisse {
    const copy: ITypeCaisse = Object.assign({}, typeCaisse, {
      dateCreated: typeCaisse.dateCreated && typeCaisse.dateCreated.isValid() ? typeCaisse.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeCaisse.dateUpdated && typeCaisse.dateUpdated.isValid() ? typeCaisse.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeCaisse: ITypeCaisse) => {
        typeCaisse.dateCreated = typeCaisse.dateCreated ? moment(typeCaisse.dateCreated) : undefined;
        typeCaisse.dateUpdated = typeCaisse.dateUpdated ? moment(typeCaisse.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
