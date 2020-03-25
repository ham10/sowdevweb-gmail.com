import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGroupeSan } from 'app/shared/model/groupe-san.model';

type EntityResponseType = HttpResponse<IGroupeSan>;
type EntityArrayResponseType = HttpResponse<IGroupeSan[]>;

@Injectable({ providedIn: 'root' })
export class GroupeSanService {
  public resourceUrl = SERVER_API_URL + 'api/groupe-sans';

  constructor(protected http: HttpClient) {}

  create(groupeSan: IGroupeSan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groupeSan);
    return this.http
      .post<IGroupeSan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(groupeSan: IGroupeSan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groupeSan);
    return this.http
      .put<IGroupeSan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGroupeSan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGroupeSan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(groupeSan: IGroupeSan): IGroupeSan {
    const copy: IGroupeSan = Object.assign({}, groupeSan, {
      dateCreated: groupeSan.dateCreated && groupeSan.dateCreated.isValid() ? groupeSan.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: groupeSan.dateUpdated && groupeSan.dateUpdated.isValid() ? groupeSan.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((groupeSan: IGroupeSan) => {
        groupeSan.dateCreated = groupeSan.dateCreated ? moment(groupeSan.dateCreated) : undefined;
        groupeSan.dateUpdated = groupeSan.dateUpdated ? moment(groupeSan.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
