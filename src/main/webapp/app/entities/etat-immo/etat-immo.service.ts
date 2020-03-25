import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatImmo } from 'app/shared/model/etat-immo.model';

type EntityResponseType = HttpResponse<IEtatImmo>;
type EntityArrayResponseType = HttpResponse<IEtatImmo[]>;

@Injectable({ providedIn: 'root' })
export class EtatImmoService {
  public resourceUrl = SERVER_API_URL + 'api/etat-immos';

  constructor(protected http: HttpClient) {}

  create(etatImmo: IEtatImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatImmo);
    return this.http
      .post<IEtatImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etatImmo: IEtatImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatImmo);
    return this.http
      .put<IEtatImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtatImmo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatImmo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etatImmo: IEtatImmo): IEtatImmo {
    const copy: IEtatImmo = Object.assign({}, etatImmo, {
      dateCreated: etatImmo.dateCreated && etatImmo.dateCreated.isValid() ? etatImmo.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: etatImmo.dateUpdated && etatImmo.dateUpdated.isValid() ? etatImmo.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: etatImmo.dateDeleted && etatImmo.dateDeleted.isValid() ? etatImmo.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((etatImmo: IEtatImmo) => {
        etatImmo.dateCreated = etatImmo.dateCreated ? moment(etatImmo.dateCreated) : undefined;
        etatImmo.dateUpdated = etatImmo.dateUpdated ? moment(etatImmo.dateUpdated) : undefined;
        etatImmo.dateDeleted = etatImmo.dateDeleted ? moment(etatImmo.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
