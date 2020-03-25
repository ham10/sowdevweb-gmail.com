import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJourFerie } from 'app/shared/model/jour-ferie.model';

type EntityResponseType = HttpResponse<IJourFerie>;
type EntityArrayResponseType = HttpResponse<IJourFerie[]>;

@Injectable({ providedIn: 'root' })
export class JourFerieService {
  public resourceUrl = SERVER_API_URL + 'api/jour-feries';

  constructor(protected http: HttpClient) {}

  create(jourFerie: IJourFerie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourFerie);
    return this.http
      .post<IJourFerie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jourFerie: IJourFerie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jourFerie);
    return this.http
      .put<IJourFerie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJourFerie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJourFerie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jourFerie: IJourFerie): IJourFerie {
    const copy: IJourFerie = Object.assign({}, jourFerie, {
      dateJourFerie: jourFerie.dateJourFerie && jourFerie.dateJourFerie.isValid() ? jourFerie.dateJourFerie.format(DATE_FORMAT) : undefined,
      dateCreated: jourFerie.dateCreated && jourFerie.dateCreated.isValid() ? jourFerie.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: jourFerie.dateUpdated && jourFerie.dateUpdated.isValid() ? jourFerie.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateJourFerie = res.body.dateJourFerie ? moment(res.body.dateJourFerie) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jourFerie: IJourFerie) => {
        jourFerie.dateJourFerie = jourFerie.dateJourFerie ? moment(jourFerie.dateJourFerie) : undefined;
        jourFerie.dateCreated = jourFerie.dateCreated ? moment(jourFerie.dateCreated) : undefined;
        jourFerie.dateUpdated = jourFerie.dateUpdated ? moment(jourFerie.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
