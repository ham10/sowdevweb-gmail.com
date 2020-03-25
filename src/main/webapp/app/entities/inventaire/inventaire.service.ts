import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventaire } from 'app/shared/model/inventaire.model';

type EntityResponseType = HttpResponse<IInventaire>;
type EntityArrayResponseType = HttpResponse<IInventaire[]>;

@Injectable({ providedIn: 'root' })
export class InventaireService {
  public resourceUrl = SERVER_API_URL + 'api/inventaires';

  constructor(protected http: HttpClient) {}

  create(inventaire: IInventaire): Observable<EntityResponseType> {
    return this.http.post<IInventaire>(this.resourceUrl, inventaire, { observe: 'response' });
  }

  update(inventaire: IInventaire): Observable<EntityResponseType> {
    return this.http.put<IInventaire>(this.resourceUrl, inventaire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
