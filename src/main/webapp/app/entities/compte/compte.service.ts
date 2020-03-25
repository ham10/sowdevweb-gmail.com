import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompte } from 'app/shared/model/compte.model';

type EntityResponseType = HttpResponse<ICompte>;
type EntityArrayResponseType = HttpResponse<ICompte[]>;

@Injectable({ providedIn: 'root' })
export class CompteService {
  public resourceUrl = SERVER_API_URL + 'api/comptes';

  constructor(protected http: HttpClient) {}

  create(compte: ICompte): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compte);
    return this.http
      .post<ICompte>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compte: ICompte): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compte);
    return this.http
      .put<ICompte>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompte>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompte[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compte: ICompte): ICompte {
    const copy: ICompte = Object.assign({}, compte, {
      dateCreated: compte.dateCreated && compte.dateCreated.isValid() ? compte.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: compte.dateUpdated && compte.dateUpdated.isValid() ? compte.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((compte: ICompte) => {
        compte.dateCreated = compte.dateCreated ? moment(compte.dateCreated) : undefined;
        compte.dateUpdated = compte.dateUpdated ? moment(compte.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
