import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';

type EntityResponseType = HttpResponse<ICatChambre>;
type EntityArrayResponseType = HttpResponse<ICatChambre[]>;

@Injectable({ providedIn: 'root' })
export class CatChambreService {
  public resourceUrl = SERVER_API_URL + 'api/cat-chambres';

  constructor(protected http: HttpClient) {}

  create(catChambre: ICatChambre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catChambre);
    return this.http
      .post<ICatChambre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(catChambre: ICatChambre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catChambre);
    return this.http
      .put<ICatChambre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICatChambre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICatChambre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(catChambre: ICatChambre): ICatChambre {
    const copy: ICatChambre = Object.assign({}, catChambre, {
      dateCreated: catChambre.dateCreated && catChambre.dateCreated.isValid() ? catChambre.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: catChambre.dateUpdated && catChambre.dateUpdated.isValid() ? catChambre.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((catChambre: ICatChambre) => {
        catChambre.dateCreated = catChambre.dateCreated ? moment(catChambre.dateCreated) : undefined;
        catChambre.dateUpdated = catChambre.dateUpdated ? moment(catChambre.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
