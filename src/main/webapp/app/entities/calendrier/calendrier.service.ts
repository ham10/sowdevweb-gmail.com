import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICalendrier } from 'app/shared/model/calendrier.model';

type EntityResponseType = HttpResponse<ICalendrier>;
type EntityArrayResponseType = HttpResponse<ICalendrier[]>;

@Injectable({ providedIn: 'root' })
export class CalendrierService {
  public resourceUrl = SERVER_API_URL + 'api/calendriers';

  constructor(protected http: HttpClient) {}

  create(calendrier: ICalendrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calendrier);
    return this.http
      .post<ICalendrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(calendrier: ICalendrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calendrier);
    return this.http
      .put<ICalendrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICalendrier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICalendrier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(calendrier: ICalendrier): ICalendrier {
    const copy: ICalendrier = Object.assign({}, calendrier, {
      heureDebut: calendrier.heureDebut && calendrier.heureDebut.isValid() ? calendrier.heureDebut.format(DATE_FORMAT) : undefined,
      heureFin: calendrier.heureFin && calendrier.heureFin.isValid() ? calendrier.heureFin.format(DATE_FORMAT) : undefined,
      dateDebut: calendrier.dateDebut && calendrier.dateDebut.isValid() ? calendrier.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: calendrier.dateFin && calendrier.dateFin.isValid() ? calendrier.dateFin.format(DATE_FORMAT) : undefined,
      dateCreated: calendrier.dateCreated && calendrier.dateCreated.isValid() ? calendrier.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: calendrier.dateUpdated && calendrier.dateUpdated.isValid() ? calendrier.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: calendrier.dateDeleted && calendrier.dateDeleted.isValid() ? calendrier.dateDeleted.format(DATE_FORMAT) : undefined,
      userCreated: calendrier.userCreated && calendrier.userCreated.isValid() ? calendrier.userCreated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureDebut = res.body.heureDebut ? moment(res.body.heureDebut) : undefined;
      res.body.heureFin = res.body.heureFin ? moment(res.body.heureFin) : undefined;
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
      res.body.userCreated = res.body.userCreated ? moment(res.body.userCreated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((calendrier: ICalendrier) => {
        calendrier.heureDebut = calendrier.heureDebut ? moment(calendrier.heureDebut) : undefined;
        calendrier.heureFin = calendrier.heureFin ? moment(calendrier.heureFin) : undefined;
        calendrier.dateDebut = calendrier.dateDebut ? moment(calendrier.dateDebut) : undefined;
        calendrier.dateFin = calendrier.dateFin ? moment(calendrier.dateFin) : undefined;
        calendrier.dateCreated = calendrier.dateCreated ? moment(calendrier.dateCreated) : undefined;
        calendrier.dateUpdated = calendrier.dateUpdated ? moment(calendrier.dateUpdated) : undefined;
        calendrier.dateDeleted = calendrier.dateDeleted ? moment(calendrier.dateDeleted) : undefined;
        calendrier.userCreated = calendrier.userCreated ? moment(calendrier.userCreated) : undefined;
      });
    }
    return res;
  }
}
