import { Moment } from 'moment';

export interface ITypeChamps {
  id?: number;
  codeTypeChamps?: string;
  libelleTypeChamps?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeChamps implements ITypeChamps {
  constructor(
    public id?: number,
    public codeTypeChamps?: string,
    public libelleTypeChamps?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
