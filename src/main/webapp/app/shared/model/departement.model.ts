import { Moment } from 'moment';
import { IEtablis } from 'app/shared/model/etablis.model';
import { IRegion } from 'app/shared/model/region.model';

export interface IDepartement {
  id?: number;
  codeDepartement?: string;
  libelleDepartement?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  etablis?: IEtablis[];
  region?: IRegion;
}

export class Departement implements IDepartement {
  constructor(
    public id?: number,
    public codeDepartement?: string,
    public libelleDepartement?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public etablis?: IEtablis[],
    public region?: IRegion
  ) {}
}
