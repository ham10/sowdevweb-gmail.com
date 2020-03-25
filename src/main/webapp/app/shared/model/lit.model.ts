import { Moment } from 'moment';
import { ITypeLit } from 'app/shared/model/type-lit.model';
import { IBoxe } from 'app/shared/model/boxe.model';

export interface ILit {
  id?: number;
  numeroLit?: number;
  descriptionLit?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typeLit?: ITypeLit;
  box?: IBoxe;
}

export class Lit implements ILit {
  constructor(
    public id?: number,
    public numeroLit?: number,
    public descriptionLit?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typeLit?: ITypeLit,
    public box?: IBoxe
  ) {}
}
