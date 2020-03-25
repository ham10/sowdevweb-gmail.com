import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICaisse } from 'app/shared/model/caisse.model';

type EntityResponseType = HttpResponse<ICaisse>;
type EntityArrayResponseType = HttpResponse<ICaisse[]>;

@Injectable({ providedIn: 'root' })
export class CaisseService {
  public resourceUrl = SERVER_API_URL + 'api/caisses';

  constructor(protected http: HttpClient) {}

  create(caisse: ICaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(caisse);
    return this.http
      .post<ICaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(caisse: ICaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(caisse);
    return this.http
      .put<ICaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICaisse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICaisse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(caisse: ICaisse): ICaisse {
    const copy: ICaisse = Object.assign({}, caisse, {
      soldeMin: caisse.soldeMin && caisse.soldeMin.isValid() ? caisse.soldeMin.format(DATE_FORMAT) : undefined,
      dateCreated: caisse.dateCreated && caisse.dateCreated.isValid() ? caisse.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: caisse.dateUpdated && caisse.dateUpdated.isValid() ? caisse.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: caisse.dateDeleted && caisse.dateDeleted.isValid() ? caisse.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.soldeMin = res.body.soldeMin ? moment(res.body.soldeMin) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((caisse: ICaisse) => {
        caisse.soldeMin = caisse.soldeMin ? moment(caisse.soldeMin) : undefined;
        caisse.dateCreated = caisse.dateCreated ? moment(caisse.dateCreated) : undefined;
        caisse.dateUpdated = caisse.dateUpdated ? moment(caisse.dateUpdated) : undefined;
        caisse.dateDeleted = caisse.dateDeleted ? moment(caisse.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
