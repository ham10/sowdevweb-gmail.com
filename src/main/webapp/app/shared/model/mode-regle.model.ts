import { Moment } from 'moment';

export interface IModeRegle {
  id?: number;
  codeModeRegle?: string;
  libelleModeRegle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class ModeRegle implements IModeRegle {
  constructor(
    public id?: number,
    public codeModeRegle?: string,
    public libelleModeRegle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
