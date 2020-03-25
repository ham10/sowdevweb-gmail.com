import { Moment } from 'moment';

export interface IMachAutorise {
  id?: number;
  numeroMachAutorise?: string;
  adresseMacMachAutorise?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class MachAutorise implements IMachAutorise {
  constructor(
    public id?: number,
    public numeroMachAutorise?: string,
    public adresseMacMachAutorise?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
