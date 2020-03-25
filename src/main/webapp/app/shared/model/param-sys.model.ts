import { Moment } from 'moment';

export interface IParamSys {
  id?: number;
  codeParamSys?: string;
  libelleParamSys?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class ParamSys implements IParamSys {
  constructor(
    public id?: number,
    public codeParamSys?: string,
    public libelleParamSys?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
