import { Moment } from 'moment';
import { IDepartement } from 'app/shared/model/departement.model';
import { IPays } from 'app/shared/model/pays.model';

export interface IRegion {
  id?: number;
  codeRegion?: string;
  libelleRegion?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  departements?: IDepartement[];
  pays?: IPays;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public codeRegion?: string,
    public libelleRegion?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public departements?: IDepartement[],
    public pays?: IPays
  ) {}
}
