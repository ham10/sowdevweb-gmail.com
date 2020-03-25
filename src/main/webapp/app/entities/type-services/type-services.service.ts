import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeServices } from 'app/shared/model/type-services.model';

type EntityResponseType = HttpResponse<ITypeServices>;
type EntityArrayResponseType = HttpResponse<ITypeServices[]>;

@Injectable({ providedIn: 'root' })
export class TypeServicesService {
  public resourceUrl = SERVER_API_URL + 'api/type-services';

  constructor(protected http: HttpClient) {}

  create(typeServices: ITypeServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeServices);
    return this.http
      .post<ITypeServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeServices: ITypeServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeServices);
    return this.http
      .put<ITypeServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeServices>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeServices[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeServices: ITypeServices): ITypeServices {
    const copy: ITypeServices = Object.assign({}, typeServices, {
      dateCreated:
        typeServices.dateCreated && typeServices.dateCreated.isValid() ? typeServices.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeServices.dateUpdated && typeServices.dateUpdated.isValid() ? typeServices.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeServices: ITypeServices) => {
        typeServices.dateCreated = typeServices.dateCreated ? moment(typeServices.dateCreated) : undefined;
        typeServices.dateUpdated = typeServices.dateUpdated ? moment(typeServices.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
