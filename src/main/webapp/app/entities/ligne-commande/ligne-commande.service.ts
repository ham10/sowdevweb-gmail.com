import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';

type EntityResponseType = HttpResponse<ILigneCommande>;
type EntityArrayResponseType = HttpResponse<ILigneCommande[]>;

@Injectable({ providedIn: 'root' })
export class LigneCommandeService {
  public resourceUrl = SERVER_API_URL + 'api/ligne-commandes';

  constructor(protected http: HttpClient) {}

  create(ligneCommande: ILigneCommande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ligneCommande);
    return this.http
      .post<ILigneCommande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ligneCommande: ILigneCommande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ligneCommande);
    return this.http
      .put<ILigneCommande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILigneCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILigneCommande[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ligneCommande: ILigneCommande): ILigneCommande {
    const copy: ILigneCommande = Object.assign({}, ligneCommande, {
      dateCreated:
        ligneCommande.dateCreated && ligneCommande.dateCreated.isValid() ? ligneCommande.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        ligneCommande.dateUpdated && ligneCommande.dateUpdated.isValid() ? ligneCommande.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((ligneCommande: ILigneCommande) => {
        ligneCommande.dateCreated = ligneCommande.dateCreated ? moment(ligneCommande.dateCreated) : undefined;
        ligneCommande.dateUpdated = ligneCommande.dateUpdated ? moment(ligneCommande.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
