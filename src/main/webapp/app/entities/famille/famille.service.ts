import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFamille } from 'app/shared/model/famille.model';

type EntityResponseType = HttpResponse<IFamille>;
type EntityArrayResponseType = HttpResponse<IFamille[]>;

@Injectable({ providedIn: 'root' })
export class FamilleService {
  public resourceUrl = SERVER_API_URL + 'api/familles';

  constructor(protected http: HttpClient) {}

  create(famille: IFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(famille);
    return this.http
      .post<IFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(famille: IFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(famille);
    return this.http
      .put<IFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFamille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFamille[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(famille: IFamille): IFamille {
    const copy: IFamille = Object.assign({}, famille, {
      dateCreated: famille.dateCreated && famille.dateCreated.isValid() ? famille.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: famille.dateUpdated && famille.dateUpdated.isValid() ? famille.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: famille.dateDeleted && famille.dateDeleted.isValid() ? famille.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((famille: IFamille) => {
        famille.dateCreated = famille.dateCreated ? moment(famille.dateCreated) : undefined;
        famille.dateUpdated = famille.dateUpdated ? moment(famille.dateUpdated) : undefined;
        famille.dateDeleted = famille.dateDeleted ? moment(famille.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
