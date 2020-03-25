import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';

type EntityResponseType = HttpResponse<IEtatBonCom>;
type EntityArrayResponseType = HttpResponse<IEtatBonCom[]>;

@Injectable({ providedIn: 'root' })
export class EtatBonComService {
  public resourceUrl = SERVER_API_URL + 'api/etat-bon-coms';

  constructor(protected http: HttpClient) {}

  create(etatBonCom: IEtatBonCom): Observable<EntityResponseType> {
    return this.http.post<IEtatBonCom>(this.resourceUrl, etatBonCom, { observe: 'response' });
  }

  update(etatBonCom: IEtatBonCom): Observable<EntityResponseType> {
    return this.http.put<IEtatBonCom>(this.resourceUrl, etatBonCom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatBonCom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatBonCom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
