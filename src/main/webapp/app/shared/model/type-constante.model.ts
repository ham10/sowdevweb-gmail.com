import { Moment } from 'moment';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

export interface ITypeConstante {
  id?: number;
  codeTypeConstante?: string;
  libelleTypeConstante?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  ficheMedicals?: IFicheMedical[];
}

export class TypeConstante implements ITypeConstante {
  constructor(
    public id?: number,
    public codeTypeConstante?: string,
    public libelleTypeConstante?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public ficheMedicals?: IFicheMedical[]
  ) {}
}
