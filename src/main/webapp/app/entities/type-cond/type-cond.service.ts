import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeCond } from 'app/shared/model/type-cond.model';

type EntityResponseType = HttpResponse<ITypeCond>;
type EntityArrayResponseType = HttpResponse<ITypeCond[]>;

@Injectable({ providedIn: 'root' })
export class TypeCondService {
  public resourceUrl = SERVER_API_URL + 'api/type-conds';

  constructor(protected http: HttpClient) {}

  create(typeCond: ITypeCond): Observable<EntityResponseType> {
    return this.http.post<ITypeCond>(this.resourceUrl, typeCond, { observe: 'response' });
  }

  update(typeCond: ITypeCond): Observable<EntityResponseType> {
    return this.http.put<ITypeCond>(this.resourceUrl, typeCond, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeCond>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeCond[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
