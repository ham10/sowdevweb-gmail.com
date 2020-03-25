import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';

export interface ITypeProd {
  id?: number;
  codeTypeProd?: string;
  libelleTypeProd?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  produits?: IProduit[];
}

export class TypeProd implements ITypeProd {
  constructor(
    public id?: number,
    public codeTypeProd?: string,
    public libelleTypeProd?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public produits?: IProduit[]
  ) {}
}
