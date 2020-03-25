import { Moment } from 'moment';
import { IPlat } from 'app/shared/model/plat.model';

export interface ITypePlat {
  id?: number;
  codeTypePlat?: string;
  libelleTypePlat?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  plats?: IPlat[];
}

export class TypePlat implements ITypePlat {
  constructor(
    public id?: number,
    public codeTypePlat?: string,
    public libelleTypePlat?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public plats?: IPlat[]
  ) {}
}
