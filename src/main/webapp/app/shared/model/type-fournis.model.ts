import { Moment } from 'moment';

export interface ITypeFournis {
  id?: number;
  codeTypeFournis?: string;
  libelleTypeFournis?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeFournis implements ITypeFournis {
  constructor(
    public id?: number,
    public codeTypeFournis?: string,
    public libelleTypeFournis?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
