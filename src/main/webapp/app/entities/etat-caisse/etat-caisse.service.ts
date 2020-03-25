import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatCaisse } from 'app/shared/model/etat-caisse.model';

type EntityResponseType = HttpResponse<IEtatCaisse>;
type EntityArrayResponseType = HttpResponse<IEtatCaisse[]>;

@Injectable({ providedIn: 'root' })
export class EtatCaisseService {
  public resourceUrl = SERVER_API_URL + 'api/etat-caisses';

  constructor(protected http: HttpClient) {}

  create(etatCaisse: IEtatCaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatCaisse);
    return this.http
      .post<IEtatCaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etatCaisse: IEtatCaisse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatCaisse);
    return this.http
      .put<IEtatCaisse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtatCaisse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatCaisse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etatCaisse: IEtatCaisse): IEtatCaisse {
    const copy: IEtatCaisse = Object.assign({}, etatCaisse, {
      dateCreated: etatCaisse.dateCreated && etatCaisse.dateCreated.isValid() ? etatCaisse.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: etatCaisse.dateUpdated && etatCaisse.dateUpdated.isValid() ? etatCaisse.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: etatCaisse.dateDeleted && etatCaisse.dateDeleted.isValid() ? etatCaisse.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((etatCaisse: IEtatCaisse) => {
        etatCaisse.dateCreated = etatCaisse.dateCreated ? moment(etatCaisse.dateCreated) : undefined;
        etatCaisse.dateUpdated = etatCaisse.dateUpdated ? moment(etatCaisse.dateUpdated) : undefined;
        etatCaisse.dateDeleted = etatCaisse.dateDeleted ? moment(etatCaisse.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
