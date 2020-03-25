import { Moment } from 'moment';
import { IServices } from 'app/shared/model/services.model';

export interface ISpecialite {
  id?: number;
  codeSpecialite?: string;
  libelleSpecialite?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  services?: IServices[];
}

export class Specialite implements ISpecialite {
  constructor(
    public id?: number,
    public codeSpecialite?: string,
    public libelleSpecialite?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public services?: IServices[]
  ) {}
}
