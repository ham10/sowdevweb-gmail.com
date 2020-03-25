import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartement } from 'app/shared/model/departement.model';

type EntityResponseType = HttpResponse<IDepartement>;
type EntityArrayResponseType = HttpResponse<IDepartement[]>;

@Injectable({ providedIn: 'root' })
export class DepartementService {
  public resourceUrl = SERVER_API_URL + 'api/departements';

  constructor(protected http: HttpClient) {}

  create(departement: IDepartement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departement);
    return this.http
      .post<IDepartement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(departement: IDepartement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departement);
    return this.http
      .put<IDepartement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDepartement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepartement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(departement: IDepartement): IDepartement {
    const copy: IDepartement = Object.assign({}, departement, {
      dateCreated: departement.dateCreated && departement.dateCreated.isValid() ? departement.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: departement.dateUpdated && departement.dateUpdated.isValid() ? departement.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((departement: IDepartement) => {
        departement.dateCreated = departement.dateCreated ? moment(departement.dateCreated) : undefined;
        departement.dateUpdated = departement.dateUpdated ? moment(departement.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
