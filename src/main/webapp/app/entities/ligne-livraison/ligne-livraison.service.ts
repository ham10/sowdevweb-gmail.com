import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';

type EntityResponseType = HttpResponse<ILigneLivraison>;
type EntityArrayResponseType = HttpResponse<ILigneLivraison[]>;

@Injectable({ providedIn: 'root' })
export class LigneLivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/ligne-livraisons';

  constructor(protected http: HttpClient) {}

  create(ligneLivraison: ILigneLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ligneLivraison);
    return this.http
      .post<ILigneLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ligneLivraison: ILigneLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ligneLivraison);
    return this.http
      .put<ILigneLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILigneLivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILigneLivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ligneLivraison: ILigneLivraison): ILigneLivraison {
    const copy: ILigneLivraison = Object.assign({}, ligneLivraison, {
      dateCreated:
        ligneLivraison.dateCreated && ligneLivraison.dateCreated.isValid() ? ligneLivraison.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        ligneLivraison.dateUpdated && ligneLivraison.dateUpdated.isValid() ? ligneLivraison.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted:
        ligneLivraison.dateDeleted && ligneLivraison.dateDeleted.isValid() ? ligneLivraison.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((ligneLivraison: ILigneLivraison) => {
        ligneLivraison.dateCreated = ligneLivraison.dateCreated ? moment(ligneLivraison.dateCreated) : undefined;
        ligneLivraison.dateUpdated = ligneLivraison.dateUpdated ? moment(ligneLivraison.dateUpdated) : undefined;
        ligneLivraison.dateDeleted = ligneLivraison.dateDeleted ? moment(ligneLivraison.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
