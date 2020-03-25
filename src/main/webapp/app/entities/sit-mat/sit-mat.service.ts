import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISitMat } from 'app/shared/model/sit-mat.model';

type EntityResponseType = HttpResponse<ISitMat>;
type EntityArrayResponseType = HttpResponse<ISitMat[]>;

@Injectable({ providedIn: 'root' })
export class SitMatService {
  public resourceUrl = SERVER_API_URL + 'api/sit-mats';

  constructor(protected http: HttpClient) {}

  create(sitMat: ISitMat): Observable<EntityResponseType> {
    return this.http.post<ISitMat>(this.resourceUrl, sitMat, { observe: 'response' });
  }

  update(sitMat: ISitMat): Observable<EntityResponseType> {
    return this.http.put<ISitMat>(this.resourceUrl, sitMat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISitMat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISitMat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
