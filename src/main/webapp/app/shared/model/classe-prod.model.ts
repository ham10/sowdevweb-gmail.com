import { Moment } from 'moment';

export interface IClasseProd {
  id?: number;
  codeClasseProd?: string;
  libelleClasseProd?: string;
  descriptionClasseProd?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class ClasseProd implements IClasseProd {
  constructor(
    public id?: number,
    public codeClasseProd?: string,
    public libelleClasseProd?: string,
    public descriptionClasseProd?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
