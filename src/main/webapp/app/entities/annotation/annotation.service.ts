import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnnotation } from 'app/shared/model/annotation.model';

type EntityResponseType = HttpResponse<IAnnotation>;
type EntityArrayResponseType = HttpResponse<IAnnotation[]>;

@Injectable({ providedIn: 'root' })
export class AnnotationService {
  public resourceUrl = SERVER_API_URL + 'api/annotations';

  constructor(protected http: HttpClient) {}

  create(annotation: IAnnotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(annotation);
    return this.http
      .post<IAnnotation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(annotation: IAnnotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(annotation);
    return this.http
      .put<IAnnotation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnnotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnnotation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(annotation: IAnnotation): IAnnotation {
    const copy: IAnnotation = Object.assign({}, annotation, {
      dateDeleted: annotation.dateDeleted && annotation.dateDeleted.isValid() ? annotation.dateDeleted.toJSON() : undefined,
      dateCreated: annotation.dateCreated && annotation.dateCreated.isValid() ? annotation.dateCreated.toJSON() : undefined,
      dateUpdated: annotation.dateUpdated && annotation.dateUpdated.isValid() ? annotation.dateUpdated.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeleted = res.body.dateDeleted ? moment(res.body.dateDeleted) : undefined;
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateUpdated = res.body.dateUpdated ? moment(res.body.dateUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((annotation: IAnnotation) => {
        annotation.dateDeleted = annotation.dateDeleted ? moment(annotation.dateDeleted) : undefined;
        annotation.dateCreated = annotation.dateCreated ? moment(annotation.dateCreated) : undefined;
        annotation.dateUpdated = annotation.dateUpdated ? moment(annotation.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
