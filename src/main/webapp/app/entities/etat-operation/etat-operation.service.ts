import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatOperation } from 'app/shared/model/etat-operation.model';

type EntityResponseType = HttpResponse<IEtatOperation>;
type EntityArrayResponseType = HttpResponse<IEtatOperation[]>;

@Injectable({ providedIn: 'root' })
export class EtatOperationService {
  public resourceUrl = SERVER_API_URL + 'api/etat-operations';

  constructor(protected http: HttpClient) {}

  create(etatOperation: IEtatOperation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatOperation);
    return this.http
      .post<IEtatOperation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etatOperation: IEtatOperation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatOperation);
    return this.http
      .put<IEtatOperation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtatOperation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatOperation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etatOperation: IEtatOperation): IEtatOperation {
    const copy: IEtatOperation = Object.assign({}, etatOperation, {
      dateCreated:
        etatOperation.dateCreated && etatOperation.dateCreated.isValid() ? etatOperation.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        etatOperation.dateUpdated && etatOperation.dateUpdated.isValid() ? etatOperation.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted:
        etatOperation.dateDeleted && etatOperation.dateDeleted.isValid() ? etatOperation.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((etatOperation: IEtatOperation) => {
        etatOperation.dateCreated = etatOperation.dateCreated ? moment(etatOperation.dateCreated) : undefined;
        etatOperation.dateUpdated = etatOperation.dateUpdated ? moment(etatOperation.dateUpdated) : undefined;
        etatOperation.dateDeleted = etatOperation.dateDeleted ? moment(etatOperation.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
