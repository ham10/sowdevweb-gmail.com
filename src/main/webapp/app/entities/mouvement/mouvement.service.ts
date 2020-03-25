import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMouvement } from 'app/shared/model/mouvement.model';

type EntityResponseType = HttpResponse<IMouvement>;
type EntityArrayResponseType = HttpResponse<IMouvement[]>;

@Injectable({ providedIn: 'root' })
export class MouvementService {
  public resourceUrl = SERVER_API_URL + 'api/mouvements';

  constructor(protected http: HttpClient) {}

  create(mouvement: IMouvement): Observable<EntityResponseType> {
    return this.http.post<IMouvement>(this.resourceUrl, mouvement, { observe: 'response' });
  }

  update(mouvement: IMouvement): Observable<EntityResponseType> {
    return this.http.put<IMouvement>(this.resourceUrl, mouvement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMouvement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMouvement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
