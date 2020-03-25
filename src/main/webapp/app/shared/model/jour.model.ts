import { Moment } from 'moment';
import { IHoraireCon } from 'app/shared/model/horaire-con.model';

export interface IJour {
  id?: number;
  libelleJour?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  horaireCons?: IHoraireCon[];
}

export class Jour implements IJour {
  constructor(
    public id?: number,
    public libelleJour?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public horaireCons?: IHoraireCon[]
  ) {}
}
