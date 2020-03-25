import { Moment } from 'moment';
import { IPlateau } from 'app/shared/model/plateau.model';

export interface ITypePlateau {
  id?: number;
  codeTypePlateau?: string;
  libelleTypePlateau?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  plateaus?: IPlateau[];
}

export class TypePlateau implements ITypePlateau {
  constructor(
    public id?: number,
    public codeTypePlateau?: string,
    public libelleTypePlateau?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public plateaus?: IPlateau[]
  ) {}
}
