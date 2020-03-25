import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeSortie } from 'app/shared/model/type-sortie.model';

type EntityResponseType = HttpResponse<ITypeSortie>;
type EntityArrayResponseType = HttpResponse<ITypeSortie[]>;

@Injectable({ providedIn: 'root' })
export class TypeSortieService {
  public resourceUrl = SERVER_API_URL + 'api/type-sorties';

  constructor(protected http: HttpClient) {}

  create(typeSortie: ITypeSortie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeSortie);
    return this.http
      .post<ITypeSortie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeSortie: ITypeSortie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeSortie);
    return this.http
      .put<ITypeSortie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeSortie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeSortie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeSortie: ITypeSortie): ITypeSortie {
    const copy: ITypeSortie = Object.assign({}, typeSortie, {
      dateCreated: typeSortie.dateCreated && typeSortie.dateCreated.isValid() ? typeSortie.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typeSortie.dateUpdated && typeSortie.dateUpdated.isValid() ? typeSortie.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typeSortie: ITypeSortie) => {
        typeSortie.dateCreated = typeSortie.dateCreated ? moment(typeSortie.dateCreated) : undefined;
        typeSortie.dateUpdated = typeSortie.dateUpdated ? moment(typeSortie.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
