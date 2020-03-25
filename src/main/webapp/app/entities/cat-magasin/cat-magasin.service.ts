import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatMagasin } from 'app/shared/model/cat-magasin.model';

type EntityResponseType = HttpResponse<ICatMagasin>;
type EntityArrayResponseType = HttpResponse<ICatMagasin[]>;

@Injectable({ providedIn: 'root' })
export class CatMagasinService {
  public resourceUrl = SERVER_API_URL + 'api/cat-magasins';

  constructor(protected http: HttpClient) {}

  create(catMagasin: ICatMagasin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catMagasin);
    return this.http
      .post<ICatMagasin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(catMagasin: ICatMagasin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catMagasin);
    return this.http
      .put<ICatMagasin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICatMagasin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICatMagasin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(catMagasin: ICatMagasin): ICatMagasin {
    const copy: ICatMagasin = Object.assign({}, catMagasin, {
      dateCreated: catMagasin.dateCreated && catMagasin.dateCreated.isValid() ? catMagasin.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: catMagasin.dateUpdated && catMagasin.dateUpdated.isValid() ? catMagasin.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((catMagasin: ICatMagasin) => {
        catMagasin.dateCreated = catMagasin.dateCreated ? moment(catMagasin.dateCreated) : undefined;
        catMagasin.dateUpdated = catMagasin.dateUpdated ? moment(catMagasin.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
