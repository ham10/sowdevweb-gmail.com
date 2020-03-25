import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegion } from 'app/shared/model/region.model';

type EntityResponseType = HttpResponse<IRegion>;
type EntityArrayResponseType = HttpResponse<IRegion[]>;

@Injectable({ providedIn: 'root' })
export class RegionService {
  public resourceUrl = SERVER_API_URL + 'api/regions';

  constructor(protected http: HttpClient) {}

  create(region: IRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .post<IRegion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(region: IRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .put<IRegion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(region: IRegion): IRegion {
    const copy: IRegion = Object.assign({}, region, {
      dateCreated: region.dateCreated && region.dateCreated.isValid() ? region.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: region.dateUpdated && region.dateUpdated.isValid() ? region.dateUpdated.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((region: IRegion) => {
        region.dateCreated = region.dateCreated ? moment(region.dateCreated) : undefined;
        region.dateUpdated = region.dateUpdated ? moment(region.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
