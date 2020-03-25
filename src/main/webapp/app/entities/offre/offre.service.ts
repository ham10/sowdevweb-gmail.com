import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOffre } from 'app/shared/model/offre.model';

type EntityResponseType = HttpResponse<IOffre>;
type EntityArrayResponseType = HttpResponse<IOffre[]>;

@Injectable({ providedIn: 'root' })
export class OffreService {
  public resourceUrl = SERVER_API_URL + 'api/offres';

  constructor(protected http: HttpClient) {}

  create(offre: IOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offre);
    return this.http
      .post<IOffre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offre: IOffre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offre);
    return this.http
      .put<IOffre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOffre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOffre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(offre: IOffre): IOffre {
    const copy: IOffre = Object.assign({}, offre, {
      libelle: offre.libelle && offre.libelle.isValid() ? offre.libelle.format(DATE_FORMAT) : undefined,
      date: offre.date && offre.date.isValid() ? offre.date.format(DATE_FORMAT) : undefined,
      dateCreated: offre.dateCreated && offre.dateCreated.isValid() ? offre.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: offre.dateUpdated && offre.dateUpdated.isValid() ? offre.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: offre.dateDeleted && offre.dateDeleted.isValid() ? offre.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.libelle = res.body.libelle ? moment(res.body.libelle) : undefined;
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((offre: IOffre) => {
        offre.libelle = offre.libelle ? moment(offre.libelle) : undefined;
        offre.date = offre.date ? moment(offre.date) : undefined;
        offre.dateCreated = offre.dateCreated ? moment(offre.dateCreated) : undefined;
        offre.dateUpdated = offre.dateUpdated ? moment(offre.dateUpdated) : undefined;
        offre.dateDeleted = offre.dateDeleted ? moment(offre.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
