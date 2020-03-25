import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompteGene } from 'app/shared/model/compte-gene.model';

type EntityResponseType = HttpResponse<ICompteGene>;
type EntityArrayResponseType = HttpResponse<ICompteGene[]>;

@Injectable({ providedIn: 'root' })
export class CompteGeneService {
  public resourceUrl = SERVER_API_URL + 'api/compte-genes';

  constructor(protected http: HttpClient) {}

  create(compteGene: ICompteGene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compteGene);
    return this.http
      .post<ICompteGene>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compteGene: ICompteGene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compteGene);
    return this.http
      .put<ICompteGene>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompteGene>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompteGene[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compteGene: ICompteGene): ICompteGene {
    const copy: ICompteGene = Object.assign({}, compteGene, {
      dateCreated: compteGene.dateCreated && compteGene.dateCreated.isValid() ? compteGene.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: compteGene.dateUpdated && compteGene.dateUpdated.isValid() ? compteGene.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: compteGene.dateDeleted && compteGene.dateDeleted.isValid() ? compteGene.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((compteGene: ICompteGene) => {
        compteGene.dateCreated = compteGene.dateCreated ? moment(compteGene.dateCreated) : undefined;
        compteGene.dateUpdated = compteGene.dateUpdated ? moment(compteGene.dateUpdated) : undefined;
        compteGene.dateDeleted = compteGene.dateDeleted ? moment(compteGene.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
