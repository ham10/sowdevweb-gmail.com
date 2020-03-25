import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

type EntityResponseType = HttpResponse<IBonLivraison>;
type EntityArrayResponseType = HttpResponse<IBonLivraison[]>;

@Injectable({ providedIn: 'root' })
export class BonLivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/bon-livraisons';

  constructor(protected http: HttpClient) {}

  create(bonLivraison: IBonLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonLivraison);
    return this.http
      .post<IBonLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bonLivraison: IBonLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonLivraison);
    return this.http
      .put<IBonLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBonLivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBonLivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bonLivraison: IBonLivraison): IBonLivraison {
    const copy: IBonLivraison = Object.assign({}, bonLivraison, {
      dateBonLivraison:
        bonLivraison.dateBonLivraison && bonLivraison.dateBonLivraison.isValid()
          ? bonLivraison.dateBonLivraison.format(DATE_FORMAT)
          : undefined,
      dateCertif: bonLivraison.dateCertif && bonLivraison.dateCertif.isValid() ? bonLivraison.dateCertif.format(DATE_FORMAT) : undefined,
      dateCreated:
        bonLivraison.dateCreated && bonLivraison.dateCreated.isValid() ? bonLivraison.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated:
        bonLivraison.dateUpdated && bonLivraison.dateUpdated.isValid() ? bonLivraison.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: bonLivraison.dateDeleted && bonLivraison.dateDeleted.isValid() ? bonLivraison.dateDeleted.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateBonLivraison = res.body.dateBonLivraison ? moment(res.body.dateBonLivraison) : undefined;
      res.body.dateCertif = res.body.dateCertif ? moment(res.body.dateCertif) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bonLivraison: IBonLivraison) => {
        bonLivraison.dateBonLivraison = bonLivraison.dateBonLivraison ? moment(bonLivraison.dateBonLivraison) : undefined;
        bonLivraison.dateCertif = bonLivraison.dateCertif ? moment(bonLivraison.dateCertif) : undefined;
        bonLivraison.dateCreated = bonLivraison.dateCreated ? moment(bonLivraison.dateCreated) : undefined;
        bonLivraison.dateUpdated = bonLivraison.dateUpdated ? moment(bonLivraison.dateUpdated) : undefined;
        bonLivraison.dateDeleted = bonLivraison.dateDeleted ? moment(bonLivraison.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
