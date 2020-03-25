import { Moment } from 'moment';
import { IServices } from 'app/shared/model/services.model';

export interface ITypeServices {
  id?: number;
  codeTypeSrv?: string;
  libelleTypeSrv?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  services?: IServices[];
}

export class TypeServices implements ITypeServices {
  constructor(
    public id?: number,
    public codeTypeSrv?: string,
    public libelleTypeSrv?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public services?: IServices[]
  ) {}
}
