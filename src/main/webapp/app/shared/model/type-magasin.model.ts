import { Moment } from 'moment';

export interface ITypeMagasin {
  id?: number;
  codeTypeMagasin?: string;
  libelleTypeMagasin?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeMagasin implements ITypeMagasin {
  constructor(
    public id?: number,
    public codeTypeMagasin?: string,
    public libelleTypeMagasin?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
