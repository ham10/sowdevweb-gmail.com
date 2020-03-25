import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServices } from 'app/shared/model/services.model';

type EntityResponseType = HttpResponse<IServices>;
type EntityArrayResponseType = HttpResponse<IServices[]>;

@Injectable({ providedIn: 'root' })
export class ServicesService {
  public resourceUrl = SERVER_API_URL + 'api/services';

  constructor(protected http: HttpClient) {}

  create(services: IServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(services);
    return this.http
      .post<IServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(services: IServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(services);
    return this.http
      .put<IServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServices>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServices[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(services: IServices): IServices {
    const copy: IServices = Object.assign({}, services, {
      description: services.description && services.description.isValid() ? services.description.format(DATE_FORMAT) : undefined,
      dateCreated: services.dateCreated && services.dateCreated.isValid() ? services.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: services.dateUpdated && services.dateUpdated.isValid() ? services.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.description = res.body.description ? moment(res.body.description) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((services: IServices) => {
        services.description = services.description ? moment(services.description) : undefined;
        services.dateCreated = services.dateCreated ? moment(services.dateCreated) : undefined;
        services.dateUpdated = services.dateUpdated ? moment(services.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
