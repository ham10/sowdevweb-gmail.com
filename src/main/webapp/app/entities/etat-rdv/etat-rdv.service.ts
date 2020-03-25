import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatRdv } from 'app/shared/model/etat-rdv.model';

type EntityResponseType = HttpResponse<IEtatRdv>;
type EntityArrayResponseType = HttpResponse<IEtatRdv[]>;

@Injectable({ providedIn: 'root' })
export class EtatRdvService {
  public resourceUrl = SERVER_API_URL + 'api/etat-rdvs';

  constructor(protected http: HttpClient) {}

  create(etatRdv: IEtatRdv): Observable<EntityResponseType> {
    return this.http.post<IEtatRdv>(this.resourceUrl, etatRdv, { observe: 'response' });
  }

  update(etatRdv: IEtatRdv): Observable<EntityResponseType> {
    return this.http.put<IEtatRdv>(this.resourceUrl, etatRdv, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatRdv>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatRdv[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
