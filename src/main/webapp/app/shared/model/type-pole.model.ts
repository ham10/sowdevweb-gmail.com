import { Moment } from 'moment';
import { IPole } from 'app/shared/model/pole.model';

export interface ITypePole {
  id?: number;
  codeTypePole?: string;
  libelleTypePole?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  poles?: IPole[];
}

export class TypePole implements ITypePole {
  constructor(
    public id?: number,
    public codeTypePole?: string,
    public libelleTypePole?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public poles?: IPole[]
  ) {}
}
