import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHoraireCon } from 'app/shared/model/horaire-con.model';

type EntityResponseType = HttpResponse<IHoraireCon>;
type EntityArrayResponseType = HttpResponse<IHoraireCon[]>;

@Injectable({ providedIn: 'root' })
export class HoraireConService {
  public resourceUrl = SERVER_API_URL + 'api/horaire-cons';

  constructor(protected http: HttpClient) {}

  create(horaireCon: IHoraireCon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horaireCon);
    return this.http
      .post<IHoraireCon>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(horaireCon: IHoraireCon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horaireCon);
    return this.http
      .put<IHoraireCon>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHoraireCon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoraireCon[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(horaireCon: IHoraireCon): IHoraireCon {
    const copy: IHoraireCon = Object.assign({}, horaireCon, {
      heureDebutHC: horaireCon.heureDebutHC && horaireCon.heureDebutHC.isValid() ? horaireCon.heureDebutHC.format(DATE_FORMAT) : undefined,
      heureFinHC: horaireCon.heureFinHC && horaireCon.heureFinHC.isValid() ? horaireCon.heureFinHC.format(DATE_FORMAT) : undefined,
      dateDebutHC: horaireCon.dateDebutHC && horaireCon.dateDebutHC.isValid() ? horaireCon.dateDebutHC.format(DATE_FORMAT) : undefined,
      dateFinHC: horaireCon.dateFinHC && horaireCon.dateFinHC.isValid() ? horaireCon.dateFinHC.format(DATE_FORMAT) : undefined,
      dateCreated: horaireCon.dateCreated && horaireCon.dateCreated.isValid() ? horaireCon.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: horaireCon.dateUpdated && horaireCon.dateUpdated.isValid() ? horaireCon.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: horaireCon.dateDeleted && horaireCon.dateDeleted.isValid() ? horaireCon.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureDebutHC = res.body.heureDebutHC ? moment(res.body.heureDebutHC) : undefined;
      res.body.heureFinHC = res.body.heureFinHC ? moment(res.body.heureFinHC) : undefined;
      res.body.dateDebutHC = res.body.dateDebutHC ? moment(res.body.dateDebutHC) : undefined;
      res.body.dateFinHC = res.body.dateFinHC ? moment(res.body.dateFinHC) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((horaireCon: IHoraireCon) => {
        horaireCon.heureDebutHC = horaireCon.heureDebutHC ? moment(horaireCon.heureDebutHC) : undefined;
        horaireCon.heureFinHC = horaireCon.heureFinHC ? moment(horaireCon.heureFinHC) : undefined;
        horaireCon.dateDebutHC = horaireCon.dateDebutHC ? moment(horaireCon.dateDebutHC) : undefined;
        horaireCon.dateFinHC = horaireCon.dateFinHC ? moment(horaireCon.dateFinHC) : undefined;
        horaireCon.dateCreated = horaireCon.dateCreated ? moment(horaireCon.dateCreated) : undefined;
        horaireCon.dateUpdated = horaireCon.dateUpdated ? moment(horaireCon.dateUpdated) : undefined;
        horaireCon.dateDeleted = horaireCon.dateDeleted ? moment(horaireCon.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
