import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarif } from 'app/shared/model/tarif.model';

type EntityResponseType = HttpResponse<ITarif>;
type EntityArrayResponseType = HttpResponse<ITarif[]>;

@Injectable({ providedIn: 'root' })
export class TarifService {
  public resourceUrl = SERVER_API_URL + 'api/tarifs';

  constructor(protected http: HttpClient) {}

  create(tarif: ITarif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarif);
    return this.http
      .post<ITarif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tarif: ITarif): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarif);
    return this.http
      .put<ITarif>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITarif>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITarif[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tarif: ITarif): ITarif {
    const copy: ITarif = Object.assign({}, tarif, {
      dateCreated: tarif.dateCreated && tarif.dateCreated.isValid() ? tarif.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: tarif.dateUpdated && tarif.dateUpdated.isValid() ? tarif.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: tarif.dateDeleted && tarif.dateDeleted.isValid() ? tarif.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((tarif: ITarif) => {
        tarif.dateCreated = tarif.dateCreated ? moment(tarif.dateCreated) : undefined;
        tarif.dateUpdated = tarif.dateUpdated ? moment(tarif.dateUpdated) : undefined;
        tarif.dateDeleted = tarif.dateDeleted ? moment(tarif.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
