import { Moment } from 'moment';
import { IServices } from 'app/shared/model/services.model';

export interface IDeptServices {
  id?: number;
  codeDeptSrv?: string;
  libelleDeptSrv?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  services?: IServices[];
}

export class DeptServices implements IDeptServices {
  constructor(
    public id?: number,
    public codeDeptSrv?: string,
    public libelleDeptSrv?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public services?: IServices[]
  ) {}
}
