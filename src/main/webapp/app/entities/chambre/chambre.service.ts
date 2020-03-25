import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChambre } from 'app/shared/model/chambre.model';

type EntityResponseType = HttpResponse<IChambre>;
type EntityArrayResponseType = HttpResponse<IChambre[]>;

@Injectable({ providedIn: 'root' })
export class ChambreService {
  public resourceUrl = SERVER_API_URL + 'api/chambres';

  constructor(protected http: HttpClient) {}

  create(chambre: IChambre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chambre);
    return this.http
      .post<IChambre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chambre: IChambre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chambre);
    return this.http
      .put<IChambre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChambre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChambre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chambre: IChambre): IChambre {
    const copy: IChambre = Object.assign({}, chambre, {
      dateCreated: chambre.dateCreated && chambre.dateCreated.isValid() ? chambre.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: chambre.dateUpdated && chambre.dateUpdated.isValid() ? chambre.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((chambre: IChambre) => {
        chambre.dateCreated = chambre.dateCreated ? moment(chambre.dateCreated) : undefined;
        chambre.dateUpdated = chambre.dateUpdated ? moment(chambre.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
