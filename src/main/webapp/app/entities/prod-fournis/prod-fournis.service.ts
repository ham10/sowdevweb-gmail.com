import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';

type EntityResponseType = HttpResponse<IProdFournis>;
type EntityArrayResponseType = HttpResponse<IProdFournis[]>;

@Injectable({ providedIn: 'root' })
export class ProdFournisService {
  public resourceUrl = SERVER_API_URL + 'api/prod-fournis';

  constructor(protected http: HttpClient) {}

  create(prodFournis: IProdFournis): Observable<EntityResponseType> {
    return this.http.post<IProdFournis>(this.resourceUrl, prodFournis, { observe: 'response' });
  }

  update(prodFournis: IProdFournis): Observable<EntityResponseType> {
    return this.http.put<IProdFournis>(this.resourceUrl, prodFournis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProdFournis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdFournis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
