import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISchemaCompta } from 'app/shared/model/schema-compta.model';

type EntityResponseType = HttpResponse<ISchemaCompta>;
type EntityArrayResponseType = HttpResponse<ISchemaCompta[]>;

@Injectable({ providedIn: 'root' })
export class SchemaComptaService {
  public resourceUrl = SERVER_API_URL + 'api/schema-comptas';

  constructor(protected http: HttpClient) {}

  create(schemaCompta: ISchemaCompta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schemaCompta);
    return this.http
      .post<ISchemaCompta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(schemaCompta: ISchemaCompta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schemaCompta);
    return this.http
      .put<ISchemaCompta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISchemaCompta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchemaCompta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(schemaCompta: ISchemaCompta): ISchemaCompta {
    const copy: ISchemaCompta = Object.assign({}, schemaCompta, {
      dateCreated:
        schemaCompta.dateCreated && schemaCompta.dateCreated.isValid() ? schemaCompta.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        schemaCompta.dateUpdated && schemaCompta.dateUpdated.isValid() ? schemaCompta.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: schemaCompta.dateDeleted && schemaCompta.dateDeleted.isValid() ? schemaCompta.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((schemaCompta: ISchemaCompta) => {
        schemaCompta.dateCreated = schemaCompta.dateCreated ? moment(schemaCompta.dateCreated) : undefined;
        schemaCompta.dateUpdated = schemaCompta.dateUpdated ? moment(schemaCompta.dateUpdated) : undefined;
        schemaCompta.dateDeleted = schemaCompta.dateDeleted ? moment(schemaCompta.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
