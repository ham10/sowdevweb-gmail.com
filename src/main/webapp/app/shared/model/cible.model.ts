import { Moment } from 'moment';

export interface ICible {
  id?: number;
  libelleCible?: string;
  idUser?: number;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdate?: number;
  userDelete?: number;
}

export class Cible implements ICible {
  constructor(
    public id?: number,
    public libelleCible?: string,
    public idUser?: number,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdate?: number,
    public userDelete?: number
  ) {}
}
