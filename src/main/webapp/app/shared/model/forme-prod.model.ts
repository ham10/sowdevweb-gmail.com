import { Moment } from 'moment';

export interface IFormeProd {
  id?: number;
  codeFormeProd?: string;
  libelleFormeProd?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class FormeProd implements IFormeProd {
  constructor(
    public id?: number,
    public codeFormeProd?: string,
    public libelleFormeProd?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
