import { Moment } from 'moment';
import { ICompte } from 'app/shared/model/compte.model';
import { IOperation } from 'app/shared/model/operation.model';

export interface IEcriture {
  id?: number;
  libelle?: string;
  date?: Moment;
  montant?: number;
  sens?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  compte?: ICompte;
  operation?: IOperation;
}

export class Ecriture implements IEcriture {
  constructor(
    public id?: number,
    public libelle?: string,
    public date?: Moment,
    public montant?: number,
    public sens?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public compte?: ICompte,
    public operation?: IOperation
  ) {}
}
