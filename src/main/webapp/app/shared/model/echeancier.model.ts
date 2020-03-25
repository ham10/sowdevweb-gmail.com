import { Moment } from 'moment';
import { IFacture } from 'app/shared/model/facture.model';

export interface IEcheancier {
  id?: number;
  code?: string;
  numero?: string;
  date?: Moment;
  datePaiement?: Moment;
  montant?: number;
  montantPaye?: number;
  capital?: number;
  frais?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  facture?: IFacture;
}

export class Echeancier implements IEcheancier {
  constructor(
    public id?: number,
    public code?: string,
    public numero?: string,
    public date?: Moment,
    public datePaiement?: Moment,
    public montant?: number,
    public montantPaye?: number,
    public capital?: number,
    public frais?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public facture?: IFacture
  ) {}
}
