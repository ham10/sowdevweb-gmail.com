import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatPlanning } from 'app/shared/model/etat-planning.model';

type EntityResponseType = HttpResponse<IEtatPlanning>;
type EntityArrayResponseType = HttpResponse<IEtatPlanning[]>;

@Injectable({ providedIn: 'root' })
export class EtatPlanningService {
  public resourceUrl = SERVER_API_URL + 'api/etat-plannings';

  constructor(protected http: HttpClient) {}

  create(etatPlanning: IEtatPlanning): Observable<EntityResponseType> {
    return this.http.post<IEtatPlanning>(this.resourceUrl, etatPlanning, { observe: 'response' });
  }

  update(etatPlanning: IEtatPlanning): Observable<EntityResponseType> {
    return this.http.put<IEtatPlanning>(this.resourceUrl, etatPlanning, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatPlanning>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatPlanning[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
