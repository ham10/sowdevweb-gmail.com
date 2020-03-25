import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFonctionnalite } from 'app/shared/model/fonctionnalite.model';

type EntityResponseType = HttpResponse<IFonctionnalite>;
type EntityArrayResponseType = HttpResponse<IFonctionnalite[]>;

@Injectable({ providedIn: 'root' })
export class FonctionnaliteService {
  public resourceUrl = SERVER_API_URL + 'api/fonctionnalites';

  constructor(protected http: HttpClient) {}

  create(fonctionnalite: IFonctionnalite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fonctionnalite);
    return this.http
      .post<IFonctionnalite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fonctionnalite: IFonctionnalite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fonctionnalite);
    return this.http
      .put<IFonctionnalite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFonctionnalite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFonctionnalite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fonctionnalite: IFonctionnalite): IFonctionnalite {
    const copy: IFonctionnalite = Object.assign({}, fonctionnalite, {
      dateCreated:
        fonctionnalite.dateCreated && fonctionnalite.dateCreated.isValid() ? fonctionnalite.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        fonctionnalite.dateUpdated && fonctionnalite.dateUpdated.isValid() ? fonctionnalite.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((fonctionnalite: IFonctionnalite) => {
        fonctionnalite.dateCreated = fonctionnalite.dateCreated ? moment(fonctionnalite.dateCreated) : undefined;
        fonctionnalite.dateUpdated = fonctionnalite.dateUpdated ? moment(fonctionnalite.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
