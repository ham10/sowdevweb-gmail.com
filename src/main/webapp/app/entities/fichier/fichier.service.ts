import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFichier } from 'app/shared/model/fichier.model';

type EntityResponseType = HttpResponse<IFichier>;
type EntityArrayResponseType = HttpResponse<IFichier[]>;

@Injectable({ providedIn: 'root' })
export class FichierService {
  public resourceUrl = SERVER_API_URL + 'api/fichiers';

  constructor(protected http: HttpClient) {}

  create(fichier: IFichier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fichier);
    return this.http
      .post<IFichier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fichier: IFichier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fichier);
    return this.http
      .put<IFichier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFichier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFichier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fichier: IFichier): IFichier {
    const copy: IFichier = Object.assign({}, fichier, {
      dateCreated: fichier.dateCreated && fichier.dateCreated.isValid() ? fichier.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: fichier.dateUpdated && fichier.dateUpdated.isValid() ? fichier.dateUpdated.format(DATE_FORMAT) : undefined,
      dateDeleted: fichier.dateDeleted && fichier.dateDeleted.isValid() ? fichier.dateDeleted.format(DATE_FORMAT) : undefined
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
      res.body.forEach((fichier: IFichier) => {
        fichier.dateCreated = fichier.dateCreated ? moment(fichier.dateCreated) : undefined;
        fichier.dateUpdated = fichier.dateUpdated ? moment(fichier.dateUpdated) : undefined;
        fichier.dateDeleted = fichier.dateDeleted ? moment(fichier.dateDeleted) : undefined;
      });
    }
    return res;
  }
}
