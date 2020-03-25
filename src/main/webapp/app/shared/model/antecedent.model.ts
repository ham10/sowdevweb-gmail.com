import { Moment } from 'moment';
import { IDosMedical } from 'app/shared/model/dos-medical.model';

export interface IAntecedent {
  id?: number;
  libelle?: string;
  description?: string;
  date?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  dosMedicals?: IDosMedical[];
}

export class Antecedent implements IAntecedent {
  constructor(
    public id?: number,
    public libelle?: string,
    public description?: string,
    public date?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public dosMedicals?: IDosMedical[]
  ) {}
}
