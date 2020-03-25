import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeptServices } from 'app/shared/model/dept-services.model';

type EntityResponseType = HttpResponse<IDeptServices>;
type EntityArrayResponseType = HttpResponse<IDeptServices[]>;

@Injectable({ providedIn: 'root' })
export class DeptServicesService {
  public resourceUrl = SERVER_API_URL + 'api/dept-services';

  constructor(protected http: HttpClient) {}

  create(deptServices: IDeptServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deptServices);
    return this.http
      .post<IDeptServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deptServices: IDeptServices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deptServices);
    return this.http
      .put<IDeptServices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeptServices>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeptServices[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deptServices: IDeptServices): IDeptServices {
    const copy: IDeptServices = Object.assign({}, deptServices, {
      dateCreated:
        deptServices.dateCreated && deptServices.dateCreated.isValid() ? deptServices.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: deptServices.dateUpdated && deptServices.dateUpdated.isValid() ? deptServices.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((deptServices: IDeptServices) => {
        deptServices.dateCreated = deptServices.dateCreated ? moment(deptServices.dateCreated) : undefined;
        deptServices.dateUpdated = deptServices.dateUpdated ? moment(deptServices.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
