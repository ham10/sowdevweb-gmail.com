import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

type EntityResponseType = HttpResponse<IHospitalisation>;
type EntityArrayResponseType = HttpResponse<IHospitalisation[]>;

@Injectable({ providedIn: 'root' })
export class HospitalisationService {
  public resourceUrl = SERVER_API_URL + 'api/hospitalisations';

  constructor(protected http: HttpClient) {}

  create(hospitalisation: IHospitalisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalisation);
    return this.http
      .post<IHospitalisation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hospitalisation: IHospitalisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalisation);
    return this.http
      .put<IHospitalisation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHospitalisation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHospitalisation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(hospitalisation: IHospitalisation): IHospitalisation {
    const copy: IHospitalisation = Object.assign({}, hospitalisation, {
      dateEntre:
        hospitalisation.dateEntre && hospitalisation.dateEntre.isValid() ? hospitalisation.dateEntre.format(DATE_FORMAT) : undefined,
      dateSortie:
        hospitalisation.dateSortie && hospitalisation.dateSortie.isValid() ? hospitalisation.dateSortie.format(DATE_FORMAT) : undefined,
      dateAccouchement:
        hospitalisation.dateAccouchement && hospitalisation.dateAccouchement.isValid()
          ? hospitalisation.dateAccouchement.toJSON()
          : undefined,
      dateOperation:
        hospitalisation.dateOperation && hospitalisation.dateOperation.isValid()
          ? hospitalisation.dateOperation.format(DATE_FORMAT)
          : undefined,
      dateReveil:
        hospitalisation.dateReveil && hospitalisation.dateReveil.isValid() ? hospitalisation.dateReveil.format(DATE_FORMAT) : undefined,
      dateCreated:
        hospitalisation.dateCreated && hospitalisation.dateCreated.isValid() ? hospitalisation.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        hospitalisation.dateUpdated && hospitalisation.dateUpdated.isValid() ? hospitalisation.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEntre = res.body.dateEntre ? moment(res.body.dateEntre) : undefined;
      res.body.dateSortie = res.body.dateSortie ? moment(res.body.dateSortie) : undefined;
      res.body.dateAccouchement = res.body.dateAccouchement ? moment(res.body.dateAccouchement) : undefined;
      res.body.dateOperation = res.body.dateOperation ? moment(res.body.dateOperation) : undefined;
      res.body.dateReveil = res.body.dateReveil ? moment(res.body.dateReveil) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hospitalisation: IHospitalisation) => {
        hospitalisation.dateEntre = hospitalisation.dateEntre ? moment(hospitalisation.dateEntre) : undefined;
        hospitalisation.dateSortie = hospitalisation.dateSortie ? moment(hospitalisation.dateSortie) : undefined;
        hospitalisation.dateAccouchement = hospitalisation.dateAccouchement ? moment(hospitalisation.dateAccouchement) : undefined;
        hospitalisation.dateOperation = hospitalisation.dateOperation ? moment(hospitalisation.dateOperation) : undefined;
        hospitalisation.dateReveil = hospitalisation.dateReveil ? moment(hospitalisation.dateReveil) : undefined;
        hospitalisation.dateCreated = hospitalisation.dateCreated ? moment(hospitalisation.dateCreated) : undefined;
        hospitalisation.dateUpdated = hospitalisation.dateUpdated ? moment(hospitalisation.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
