import { Moment } from 'moment';

export interface IParamCode {
  id?: number;
  codeParamCode?: string;
  libelleParamCode?: string;
  descriptionParamCode?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class ParamCode implements IParamCode {
  constructor(
    public id?: number,
    public codeParamCode?: string,
    public libelleParamCode?: string,
    public descriptionParamCode?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
