import { Moment } from 'moment';
import { IMouvement } from 'app/shared/model/mouvement.model';
import { ITypeSortie } from 'app/shared/model/type-sortie.model';

export interface ITypeMvtStock {
  id?: number;
  codeTypeMvtStock?: string;
  libelleTypeMvtStock?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  mouvements?: IMouvement[];
  typeSortie?: ITypeSortie;
}

export class TypeMvtStock implements ITypeMvtStock {
  constructor(
    public id?: number,
    public codeTypeMvtStock?: string,
    public libelleTypeMvtStock?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public mouvements?: IMouvement[],
    public typeSortie?: ITypeSortie
  ) {}
}
