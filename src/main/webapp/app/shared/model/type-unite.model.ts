import { Moment } from 'moment';
import { IUnite } from 'app/shared/model/unite.model';

export interface ITypeUnite {
  id?: number;
  codeTypeUnite?: string;
  libelleTypeUnite?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  unites?: IUnite[];
}

export class TypeUnite implements ITypeUnite {
  constructor(
    public id?: number,
    public codeTypeUnite?: string,
    public libelleTypeUnite?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public unites?: IUnite[]
  ) {}
}
