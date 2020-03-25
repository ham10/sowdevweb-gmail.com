import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/operation.model';
import { IEcheancier } from 'app/shared/model/echeancier.model';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

export interface IFacture {
  id?: number;
  designation?: string;
  montantFact?: number;
  montantPaye?: number;
  montantGlobal?: number;
  moratoire?: boolean;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  operations?: IOperation[];
  echeanciers?: IEcheancier[];
  bonLivraison?: IBonLivraison;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public designation?: string,
    public montantFact?: number,
    public montantPaye?: number,
    public montantGlobal?: number,
    public moratoire?: boolean,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public operations?: IOperation[],
    public echeanciers?: IEcheancier[],
    public bonLivraison?: IBonLivraison
  ) {
    this.moratoire = this.moratoire || false;
  }
}
