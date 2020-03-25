import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatReport } from 'app/shared/model/cat-report.model';

type EntityResponseType = HttpResponse<ICatReport>;
type EntityArrayResponseType = HttpResponse<ICatReport[]>;

@Injectable({ providedIn: 'root' })
export class CatReportService {
  public resourceUrl = SERVER_API_URL + 'api/cat-reports';

  constructor(protected http: HttpClient) {}

  create(catReport: ICatReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catReport);
    return this.http
      .post<ICatReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(catReport: ICatReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(catReport);
    return this.http
      .put<ICatReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICatReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICatReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(catReport: ICatReport): ICatReport {
    const copy: ICatReport = Object.assign({}, catReport, {
      dateCreated: catReport.dateCreated && catReport.dateCreated.isValid() ? catReport.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: catReport.dateUpdated && catReport.dateUpdated.isValid() ? catReport.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((catReport: ICatReport) => {
        catReport.dateCreated = catReport.dateCreated ? moment(catReport.dateCreated) : undefined;
        catReport.dateUpdated = catReport.dateUpdated ? moment(catReport.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
