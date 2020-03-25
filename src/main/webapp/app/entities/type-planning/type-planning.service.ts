import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePlanning } from 'app/shared/model/type-planning.model';

type EntityResponseType = HttpResponse<ITypePlanning>;
type EntityArrayResponseType = HttpResponse<ITypePlanning[]>;

@Injectable({ providedIn: 'root' })
export class TypePlanningService {
  public resourceUrl = SERVER_API_URL + 'api/type-plannings';

  constructor(protected http: HttpClient) {}

  create(typePlanning: ITypePlanning): Observable<EntityResponseType> {
    return this.http.post<ITypePlanning>(this.resourceUrl, typePlanning, { observe: 'response' });
  }

  update(typePlanning: ITypePlanning): Observable<EntityResponseType> {
    return this.http.put<ITypePlanning>(this.resourceUrl, typePlanning, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypePlanning>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypePlanning[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
