import { Moment } from 'moment';
import { ITypeUnite } from 'app/shared/model/type-unite.model';

export interface IUnite {
  id?: number;
  codeUnite?: string;
  libelleUnite?: string;
  descriptionUnite?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typeUnite?: ITypeUnite;
}

export class Unite implements IUnite {
  constructor(
    public id?: number,
    public codeUnite?: string,
    public libelleUnite?: string,
    public descriptionUnite?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typeUnite?: ITypeUnite
  ) {}
}
