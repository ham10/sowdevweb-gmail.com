import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITauxDevise } from 'app/shared/model/taux-devise.model';

type EntityResponseType = HttpResponse<ITauxDevise>;
type EntityArrayResponseType = HttpResponse<ITauxDevise[]>;

@Injectable({ providedIn: 'root' })
export class TauxDeviseService {
  public resourceUrl = SERVER_API_URL + 'api/taux-devises';

  constructor(protected http: HttpClient) {}

  create(tauxDevise: ITauxDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tauxDevise);
    return this.http
      .post<ITauxDevise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tauxDevise: ITauxDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tauxDevise);
    return this.http
      .put<ITauxDevise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITauxDevise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITauxDevise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tauxDevise: ITauxDevise): ITauxDevise {
    const copy: ITauxDevise = Object.assign({}, tauxDevise, {
      dateCreated: tauxDevise.dateCreated && tauxDevise.dateCreated.isValid() ? tauxDevise.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: tauxDevise.dateUpdated && tauxDevise.dateUpdated.isValid() ? tauxDevise.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((tauxDevise: ITauxDevise) => {
        tauxDevise.dateCreated = tauxDevise.dateCreated ? moment(tauxDevise.dateCreated) : undefined;
        tauxDevise.dateUpdated = tauxDevise.dateUpdated ? moment(tauxDevise.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
