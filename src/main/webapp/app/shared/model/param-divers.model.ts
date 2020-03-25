import { Moment } from 'moment';

export interface IParamDivers {
  id?: number;
  codeParamDivers?: string;
  libelleParamDivers?: string;
  descriptionParamDivers?: string;
  valeurNum1?: number;
  valeurNum2?: number;
  valeurNum3?: number;
  valeurText1?: string;
  valeurText2?: string;
  valeurText3?: string;
  valeurBoolean1?: boolean;
  valeurBoolean2?: boolean;
  valeurBoolean3?: boolean;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class ParamDivers implements IParamDivers {
  constructor(
    public id?: number,
    public codeParamDivers?: string,
    public libelleParamDivers?: string,
    public descriptionParamDivers?: string,
    public valeurNum1?: number,
    public valeurNum2?: number,
    public valeurNum3?: number,
    public valeurText1?: string,
    public valeurText2?: string,
    public valeurText3?: string,
    public valeurBoolean1?: boolean,
    public valeurBoolean2?: boolean,
    public valeurBoolean3?: boolean,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {
    this.valeurBoolean1 = this.valeurBoolean1 || false;
    this.valeurBoolean2 = this.valeurBoolean2 || false;
    this.valeurBoolean3 = this.valeurBoolean3 || false;
  }
}
