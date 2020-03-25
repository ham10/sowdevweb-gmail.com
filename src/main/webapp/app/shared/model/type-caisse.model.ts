import { Moment } from 'moment';

export interface ITypeCaisse {
  id?: number;
  codeTypeCaisse?: string;
  libelleTypeCaisse?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeCaisse implements ITypeCaisse {
  constructor(
    public id?: number,
    public codeTypeCaisse?: string,
    public libelleTypeCaisse?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
