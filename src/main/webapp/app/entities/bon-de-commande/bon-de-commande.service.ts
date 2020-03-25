import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

type EntityResponseType = HttpResponse<IBonDeCommande>;
type EntityArrayResponseType = HttpResponse<IBonDeCommande[]>;

@Injectable({ providedIn: 'root' })
export class BonDeCommandeService {
  public resourceUrl = SERVER_API_URL + 'api/bon-de-commandes';

  constructor(protected http: HttpClient) {}

  create(bonDeCommande: IBonDeCommande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonDeCommande);
    return this.http
      .post<IBonDeCommande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bonDeCommande: IBonDeCommande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonDeCommande);
    return this.http
      .put<IBonDeCommande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBonDeCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBonDeCommande[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bonDeCommande: IBonDeCommande): IBonDeCommande {
    const copy: IBonDeCommande = Object.assign({}, bonDeCommande, {
      dateComm: bonDeCommande.dateComm && bonDeCommande.dateComm.isValid() ? bonDeCommande.dateComm.format(DATE_FORMAT) : undefined,
      dateCreated:
        bonDeCommande.dateCreated && bonDeCommande.dateCreated.isValid() ? bonDeCommande.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        bonDeCommande.dateUpdated && bonDeCommande.dateUpdated.isValid() ? bonDeCommande.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted:
        bonDeCommande.dateDeleted && bonDeCommande.dateDeleted.isValid() ? bonDeCommande.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateComm = res.body.dateComm ? moment(res.body.dateComm) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bonDeCommande: IBonDeCommande) => {
        bonDeCommande.dateComm = bonDeCommande.dateComm ? moment(bonDeCommande.dateComm) : undefined;
        bonDeCommande.dateCreated = bonDeCommande.dateCreated ? moment(bonDeCommande.dateCreated) : undefined;
        bonDeCommande.dateUpdated = bonDeCommande.dateUpdated ? moment(bonDeCommande.dateUpdated) : undefined;
        bonDeCommande.dateDeleted = bonDeCommande.dateDeleted ? moment(bonDeCommande.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
