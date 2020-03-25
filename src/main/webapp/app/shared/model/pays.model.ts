import { Moment } from 'moment';
import { IRegion } from 'app/shared/model/region.model';

export interface IPays {
  id?: number;
  codePays?: string;
  libellePays?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  regions?: IRegion[];
}

export class Pays implements IPays {
  constructor(
    public id?: number,
    public codePays?: string,
    public libellePays?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public regions?: IRegion[]
  ) {}
}
