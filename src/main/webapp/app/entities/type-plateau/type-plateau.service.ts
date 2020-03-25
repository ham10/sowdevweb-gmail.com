import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePlateau } from 'app/shared/model/type-plateau.model';

type EntityResponseType = HttpResponse<ITypePlateau>;
type EntityArrayResponseType = HttpResponse<ITypePlateau[]>;

@Injectable({ providedIn: 'root' })
export class TypePlateauService {
  public resourceUrl = SERVER_API_URL + 'api/type-plateaus';

  constructor(protected http: HttpClient) {}

  create(typePlateau: ITypePlateau): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePlateau);
    return this.http
      .post<ITypePlateau>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePlateau: ITypePlateau): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePlateau);
    return this.http
      .put<ITypePlateau>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePlateau>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePlateau[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePlateau: ITypePlateau): ITypePlateau {
    const copy: ITypePlateau = Object.assign({}, typePlateau, {
      dateCreated: typePlateau.dateCreated && typePlateau.dateCreated.isValid() ? typePlateau.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePlateau.dateUpdated && typePlateau.dateUpdated.isValid() ? typePlateau.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePlateau: ITypePlateau) => {
        typePlateau.dateCreated = typePlateau.dateCreated ? moment(typePlateau.dateCreated) : undefined;
        typePlateau.dateUpdated = typePlateau.dateUpdated ? moment(typePlateau.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
