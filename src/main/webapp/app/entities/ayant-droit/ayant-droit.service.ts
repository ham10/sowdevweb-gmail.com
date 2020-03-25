import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAyantDroit } from 'app/shared/model/ayant-droit.model';

type EntityResponseType = HttpResponse<IAyantDroit>;
type EntityArrayResponseType = HttpResponse<IAyantDroit[]>;

@Injectable({ providedIn: 'root' })
export class AyantDroitService {
  public resourceUrl = SERVER_API_URL + 'api/ayant-droits';

  constructor(protected http: HttpClient) {}

  create(ayantDroit: IAyantDroit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ayantDroit);
    return this.http
      .post<IAyantDroit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ayantDroit: IAyantDroit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ayantDroit);
    return this.http
      .put<IAyantDroit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAyantDroit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAyantDroit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ayantDroit: IAyantDroit): IAyantDroit {
    const copy: IAyantDroit = Object.assign({}, ayantDroit, {
      dateNais: ayantDroit.dateNais && ayantDroit.dateNais.isValid() ? ayantDroit.dateNais.format(DATE_FORMAT) : undefined,
      dateCreated: ayantDroit.dateCreated && ayantDroit.dateCreated.isValid() ? ayantDroit.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: ayantDroit.dateUpdated && ayantDroit.dateUpdated.isValid() ? ayantDroit.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: ayantDroit.dateDeleted && ayantDroit.dateDeleted.isValid() ? ayantDroit.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNais = res.body.dateNais ? moment(res.body.dateNais) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ayantDroit: IAyantDroit) => {
        ayantDroit.dateNais = ayantDroit.dateNais ? moment(ayantDroit.dateNais) : undefined;
        ayantDroit.dateCreated = ayantDroit.dateCreated ? moment(ayantDroit.dateCreated) : undefined;
        ayantDroit.dateUpdated = ayantDroit.dateUpdated ? moment(ayantDroit.dateUpdated) : undefined;
        ayantDroit.dateDeleted = ayantDroit.dateDeleted ? moment(ayantDroit.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
