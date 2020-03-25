import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModule } from 'app/shared/model/module.model';

type EntityResponseType = HttpResponse<IModule>;
type EntityArrayResponseType = HttpResponse<IModule[]>;

@Injectable({ providedIn: 'root' })
export class ModuleService {
  public resourceUrl = SERVER_API_URL + 'api/modules';

  constructor(protected http: HttpClient) {}

  create(module: IModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(module);
    return this.http
      .post<IModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(module: IModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(module);
    return this.http
      .put<IModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(module: IModule): IModule {
    const copy: IModule = Object.assign({}, module, {
      dateCreated: module.dateCreated && module.dateCreated.isValid() ? module.dateCreated.format(DATE_FORMAT) : undefined,
      dateUpdated: module.dateUpdated && module.dateUpdated.isValid() ? module.dateUpdated.format(DATE_FORMAT) : undefined
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
      res.body.forEach((module: IModule) => {
        module.dateCreated = module.dateCreated ? moment(module.dateCreated) : undefined;
        module.dateUpdated = module.dateUpdated ? moment(module.dateUpdated) : undefined;
      });
    }
    return res;
  }
}
