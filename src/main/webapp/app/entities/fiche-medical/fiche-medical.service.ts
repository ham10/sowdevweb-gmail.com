import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

type EntityResponseType = HttpResponse<IFicheMedical>;
type EntityArrayResponseType = HttpResponse<IFicheMedical[]>;

@Injectable({ providedIn: 'root' })
export class FicheMedicalService {
  public resourceUrl = SERVER_API_URL + 'api/fiche-medicals';

  constructor(protected http: HttpClient) {}

  create(ficheMedical: IFicheMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheMedical);
    return this.http
      .post<IFicheMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ficheMedical: IFicheMedical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheMedical);
    return this.http
      .put<IFicheMedical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFicheMedical>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFicheMedical[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ficheMedical: IFicheMedical): IFicheMedical {
    const copy: IFicheMedical = Object.assign({}, ficheMedical, {
      dateConsultation:
        ficheMedical.dateConsultation && ficheMedical.dateConsultation.isValid()
          ? ficheMedical.dateConsultation.format(DATE_FORMAT)
          : undefined,
      dateProchainRV:
        ficheMedical.dateProchainRV && ficheMedical.dateProchainRV.isValid() ? ficheMedical.dateProchainRV.format(DATE_FORMAT) : undefined,
      dateCreated:
        ficheMedical.dateCreated && ficheMedical.dateCreated.isValid() ? ficheMedical.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: ficheMedical.dateUpdated && ficheMedical.dateUpdated.isValid() ? ficheMedical.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateConsultation = res.body.dateConsultation ? moment(res.body.dateConsultation) : undefined;
      res.body.dateProchainRV = res.body.dateProchainRV ? moment(res.body.dateProchainRV) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ficheMedical: IFicheMedical) => {
        ficheMedical.dateConsultation = ficheMedical.dateConsultation ? moment(ficheMedical.dateConsultation) : undefined;
        ficheMedical.dateProchainRV = ficheMedical.dateProchainRV ? moment(ficheMedical.dateProchainRV) : undefined;
        ficheMedical.dateCreated = ficheMedical.dateCreated ? moment(ficheMedical.dateCreated) : undefined;
        ficheMedical.dateUpdated = ficheMedical.dateUpdated ? moment(ficheMedical.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
