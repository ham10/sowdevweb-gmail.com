import { Moment } from 'moment';
import { ICodeBudget } from 'app/shared/model/code-budget.model';
import { IBanque } from 'app/shared/model/banque.model';
import { IServices } from 'app/shared/model/services.model';
import { ICompte } from 'app/shared/model/compte.model';
import { IChapCompta } from 'app/shared/model/chap-compta.model';

export interface ICompteGene {
  id?: number;
  numeroCompteGene?: number;
  libelleCompteGene?: string;
  sensCompteGene?: string;
  soldeCompteGene?: number;
  cumulMouvDebitCompteGene?: number;
  cumulMouvCreditCompteGene?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  codeBudgets?: ICodeBudget[];
  banques?: IBanque[];
  services?: IServices[];
  comptes?: ICompte[];
  chapitreComptable?: IChapCompta;
}

export class CompteGene implements ICompteGene {
  constructor(
    public id?: number,
    public numeroCompteGene?: number,
    public libelleCompteGene?: string,
    public sensCompteGene?: string,
    public soldeCompteGene?: number,
    public cumulMouvDebitCompteGene?: number,
    public cumulMouvCreditCompteGene?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public codeBudgets?: ICodeBudget[],
    public banques?: IBanque[],
    public services?: IServices[],
    public comptes?: ICompte[],
    public chapitreComptable?: IChapCompta
  ) {}
}
