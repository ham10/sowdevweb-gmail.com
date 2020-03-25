import { Moment } from 'moment';
import { ITypePole } from 'app/shared/model/type-pole.model';

export interface IPole {
  id?: number;
  codePole?: string;
  libellePole?: string;
  descriptionPole?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typePole?: ITypePole;
}

export class Pole implements IPole {
  constructor(
    public id?: number,
    public codePole?: string,
    public libellePole?: string,
    public descriptionPole?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typePole?: ITypePole
  ) {}
}
