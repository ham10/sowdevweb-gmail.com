import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedecin } from 'app/shared/model/medecin.model';
import { IPatient } from 'app/shared/model/patient.model';

type EntityResponseType = HttpResponse<IMedecin>;
type EntityArrayResponseType = HttpResponse<IMedecin[]>;

@Injectable({ providedIn: 'root' })
export class MedecinService {
  public resourceUrl = SERVER_API_URL + 'api/medecins';

  constructor(protected http: HttpClient) {}

  create(medecin: IMedecin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medecin);
    return this.http
      .post<IMedecin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medecin: IMedecin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medecin);
    return this.http
      .put<IMedecin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedecin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByNumPiece(numpiece: Number): Observable<EntityResponseType> {
    return this.http
      .get<IMedecin>(`${SERVER_API_URL}/api/medecinss/${numpiece}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedecin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medecin: IMedecin): IMedecin {
    const copy: IMedecin = Object.assign({}, medecin, {
      dateNaissance: medecin.dateNaissance && medecin.dateNaissance.isValid() ? medecin.dateNaissance.format(DATE_FORMAT) : undefined,
      dateEmbauche: medecin.dateEmbauche && medecin.dateEmbauche.isValid() ? medecin.dateEmbauche.format(DATE_FORMAT) : undefined,
      dateValidite: medecin.dateValidite && medecin.dateValidite.isValid() ? medecin.dateValidite.format(DATE_FORMAT) : undefined,
      dateCreated: medecin.dateCreated && medecin.dateCreated.isValid() ? medecin.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: medecin.dateUpdated && medecin.dateUpdated.isValid() ? medecin.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
      res.body.dateEmbauche = res.body.dateEmbauche ? moment(res.body.dateEmbauche) : undefined;
      res.body.dateValidite = res.body.dateValidite ? moment(res.body.dateValidite) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medecin: IMedecin) => {
        medecin.dateNaissance = medecin.dateNaissance ? moment(medecin.dateNaissance) : undefined;
        medecin.dateEmbauche = medecin.dateEmbauche ? moment(medecin.dateEmbauche) : undefined;
        medecin.dateValidite = medecin.dateValidite ? moment(medecin.dateValidite) : undefined;
        medecin.dateCreated = medecin.dateCreated ? moment(medecin.dateCreated) : undefined;
        medecin.dateUpdated = medecin.dateUpdated ? moment(medecin.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
