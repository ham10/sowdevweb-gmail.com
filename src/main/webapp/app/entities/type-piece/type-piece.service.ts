import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePiece } from 'app/shared/model/type-piece.model';

type EntityResponseType = HttpResponse<ITypePiece>;
type EntityArrayResponseType = HttpResponse<ITypePiece[]>;

@Injectable({ providedIn: 'root' })
export class TypePieceService {
  public resourceUrl = SERVER_API_URL + 'api/type-pieces';

  constructor(protected http: HttpClient) {}

  create(typePiece: ITypePiece): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePiece);
    return this.http
      .post<ITypePiece>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typePiece: ITypePiece): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typePiece);
    return this.http
      .put<ITypePiece>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypePiece>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypePiece[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typePiece: ITypePiece): ITypePiece {
    const copy: ITypePiece = Object.assign({}, typePiece, {
      dateCreated: typePiece.dateCreated && typePiece.dateCreated.isValid() ? typePiece.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: typePiece.dateUpdated && typePiece.dateUpdated.isValid() ? typePiece.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((typePiece: ITypePiece) => {
        typePiece.dateCreated = typePiece.dateCreated ? moment(typePiece.dateCreated) : undefined;
        typePiece.dateUpdated = typePiece.dateUpdated ? moment(typePiece.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
