import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFournisseur } from 'app/shared/model/fournisseur.model';

type EntityResponseType = HttpResponse<IFournisseur>;
type EntityArrayResponseType = HttpResponse<IFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class FournisseurService {
  public resourceUrl = SERVER_API_URL + 'api/fournisseurs';

  constructor(protected http: HttpClient) {}

  create(fournisseur: IFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournisseur);
    return this.http
      .post<IFournisseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fournisseur: IFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournisseur);
    return this.http
      .put<IFournisseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fournisseur: IFournisseur): IFournisseur {
    const copy: IFournisseur = Object.assign({}, fournisseur, {
      dateCreated: fournisseur.dateCreated && fournisseur.dateCreated.isValid() ? fournisseur.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: fournisseur.dateUpdated && fournisseur.dateUpdated.isValid() ? fournisseur.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((fournisseur: IFournisseur) => {
        fournisseur.dateCreated = fournisseur.dateCreated ? moment(fournisseur.dateCreated) : undefined;
        fournisseur.dateUpdated = fournisseur.dateUpdated ? moment(fournisseur.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
