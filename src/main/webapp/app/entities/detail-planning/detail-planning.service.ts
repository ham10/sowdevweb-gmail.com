import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDetailPlanning } from 'app/shared/model/detail-planning.model';

type EntityResponseType = HttpResponse<IDetailPlanning>;
type EntityArrayResponseType = HttpResponse<IDetailPlanning[]>;

@Injectable({ providedIn: 'root' })
export class DetailPlanningService {
  public resourceUrl = SERVER_API_URL + 'api/detail-plannings';

  constructor(protected http: HttpClient) {}

  create(detailPlanning: IDetailPlanning): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailPlanning);
    return this.http
      .post<IDetailPlanning>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detailPlanning: IDetailPlanning): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailPlanning);
    return this.http
      .put<IDetailPlanning>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetailPlanning>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetailPlanning[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(detailPlanning: IDetailPlanning): IDetailPlanning {
    const copy: IDetailPlanning = Object.assign({}, detailPlanning, {
      dateDebut: detailPlanning.dateDebut && detailPlanning.dateDebut.isValid() ? detailPlanning.dateDebut.toJSON() : undefined,
      dateFin: detailPlanning.dateFin && detailPlanning.dateFin.isValid() ? detailPlanning.dateFin.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((detailPlanning: IDetailPlanning) => {
        detailPlanning.dateDebut = detailPlanning.dateDebut ? moment(detailPlanning.dateDebut) : undefined;
        detailPlanning.dateFin = detailPlanning.dateFin ? moment(detailPlanning.dateFin) : undefined;
      });
    }
    return res;
  }
}
