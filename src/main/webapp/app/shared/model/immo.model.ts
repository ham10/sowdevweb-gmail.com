import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/operation.model';
import { ITypeImmo } from 'app/shared/model/type-immo.model';
import { ITabAmortis } from 'app/shared/model/tab-amortis.model';

export interface IImmo {
  id?: number;
  libelle?: string;
  valeurAquisition?: number;
  dateAquisition?: Moment;
  valeurNetComptable?: number;
  montant?: number;
  duree?: number;
  nbreEcheance?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  operation?: IOperation;
  typeImmo?: ITypeImmo;
  tableauAmortissement?: ITabAmortis;
}

export class Immo implements IImmo {
  constructor(
    public id?: number,
    public libelle?: string,
    public valeurAquisition?: number,
    public dateAquisition?: Moment,
    public valeurNetComptable?: number,
    public montant?: number,
    public duree?: number,
    public nbreEcheance?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public operation?: IOperation,
    public typeImmo?: ITypeImmo,
    public tableauAmortissement?: ITabAmortis
  ) {}
}
