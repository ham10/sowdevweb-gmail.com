import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISousFamille } from 'app/shared/model/sous-famille.model';

type EntityResponseType = HttpResponse<ISousFamille>;
type EntityArrayResponseType = HttpResponse<ISousFamille[]>;

@Injectable({ providedIn: 'root' })
export class SousFamilleService {
  public resourceUrl = SERVER_API_URL + 'api/sous-familles';

  constructor(protected http: HttpClient) {}

  create(sousFamille: ISousFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousFamille);
    return this.http
      .post<ISousFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sousFamille: ISousFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousFamille);
    return this.http
      .put<ISousFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISousFamille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISousFamille[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sousFamille: ISousFamille): ISousFamille {
    const copy: ISousFamille = Object.assign({}, sousFamille, {
      dateCreated: sousFamille.dateCreated && sousFamille.dateCreated.isValid() ? sousFamille.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: sousFamille.dateUpdated && sousFamille.dateUpdated.isValid() ? sousFamille.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: sousFamille.dateDeleted && sousFamille.dateDeleted.isValid() ? sousFamille.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((sousFamille: ISousFamille) => {
        sousFamille.dateCreated = sousFamille.dateCreated ? moment(sousFamille.dateCreated) : undefined;
        sousFamille.dateUpdated = sousFamille.dateUpdated ? moment(sousFamille.dateUpdated) : undefined;
        sousFamille.dateDeleted = sousFamille.dateDeleted ? moment(sousFamille.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
