import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';

type EntityResponseType = HttpResponse<ITypeTarif>;
type EntityArrayResponseType = HttpResponse<ITypeTarif[]>;

@Injectable({ providedIn: 'root' })
export class TypeTarifService {
  public resourceUrl = SERVER_API_URL + 'api/type-tarifs';

  constructor(protected http: HttpClient) {}

  create(typeTarif: ITypeTarif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeTarif);
    return this.http
      .post<ITypeTarif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeTarif: ITypeTarif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeTarif);
    return this.http
      .put<ITypeTarif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeTarif>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeTarif[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeTarif: ITypeTarif): ITypeTarif {
    const copy: ITypeTarif = Object.assign({}, typeTarif, {
      dateCreated: typeTarif.dateCreated && typeTarif.dateCreated.isValid() ? typeTarif.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeTarif.dateUpdated && typeTarif.dateUpdated.isValid() ? typeTarif.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: typeTarif.dateDeleted && typeTarif.dateDeleted.isValid() ? typeTarif.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((typeTarif: ITypeTarif) => {
        typeTarif.dateCreated = typeTarif.dateCreated ? moment(typeTarif.dateCreated) : undefined;
        typeTarif.dateUpdated = typeTarif.dateUpdated ? moment(typeTarif.dateUpdated) : undefined;
        typeTarif.dateDeleted = typeTarif.dateDeleted ? moment(typeTarif.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
