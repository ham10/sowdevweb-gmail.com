import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtagere } from 'app/shared/model/etagere.model';

type EntityResponseType = HttpResponse<IEtagere>;
type EntityArrayResponseType = HttpResponse<IEtagere[]>;

@Injectable({ providedIn: 'root' })
export class EtagereService {
  public resourceUrl = SERVER_API_URL + 'api/etageres';

  constructor(protected http: HttpClient) {}

  create(etagere: IEtagere): Observable<EntityResponseType> {
    return this.http.post<IEtagere>(this.resourceUrl, etagere, { observe: 'response' });
  }

  update(etagere: IEtagere): Observable<EntityResponseType> {
    return this.http.put<IEtagere>(this.resourceUrl, etagere, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtagere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtagere[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
