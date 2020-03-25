import { Moment } from 'moment';
import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

export interface ITypeSortie {
  id?: number;
  codeTypeSortie?: string;
  libelleTypeSortie?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typeMvtStocks?: ITypeMvtStock[];
}

export class TypeSortie implements ITypeSortie {
  constructor(
    public id?: number,
    public codeTypeSortie?: string,
    public libelleTypeSortie?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typeMvtStocks?: ITypeMvtStock[]
  ) {}
}
