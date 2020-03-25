import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImmo } from 'app/shared/model/immo.model';

type EntityResponseType = HttpResponse<IImmo>;
type EntityArrayResponseType = HttpResponse<IImmo[]>;

@Injectable({ providedIn: 'root' })
export class ImmoService {
  public resourceUrl = SERVER_API_URL + 'api/immos';

  constructor(protected http: HttpClient) {}

  create(immo: IImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immo);
    return this.http
      .post<IImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(immo: IImmo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immo);
    return this.http
      .put<IImmo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImmo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImmo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(immo: IImmo): IImmo {
    const copy: IImmo = Object.assign({}, immo, {
      dateAquisition: immo.dateAquisition && immo.dateAquisition.isValid() ? immo.dateAquisition.format(DATE_FORMAT) : undefined,
      dateCreated: immo.dateCreated && immo.dateCreated.isValid() ? immo.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: immo.dateUpdated && immo.dateUpdated.isValid() ? immo.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: immo.dateDeleted && immo.dateDeleted.isValid() ? immo.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAquisition = res.body.dateAquisition ? moment(res.body.dateAquisition) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((immo: IImmo) => {
        immo.dateAquisition = immo.dateAquisition ? moment(immo.dateAquisition) : undefined;
        immo.dateCreated = immo.dateCreated ? moment(immo.dateCreated) : undefined;
        immo.dateUpdated = immo.dateUpdated ? moment(immo.dateUpdated) : undefined;
        immo.dateDeleted = immo.dateDeleted ? moment(immo.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
