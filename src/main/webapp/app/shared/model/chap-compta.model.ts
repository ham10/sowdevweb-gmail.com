import { Moment } from 'moment';
import { ICompteGene } from 'app/shared/model/compte-gene.model';

export interface IChapCompta {
  id?: number;
  numeroChapCompta?: number;
  libelleChapCompta?: string;
  sensChapCompta?: string;
  soldeChapCompta?: number;
  cumulMouvDebitChapCompta?: number;
  cumulMouvCreditChapCompta?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  compteGenes?: ICompteGene[];
}

export class ChapCompta implements IChapCompta {
  constructor(
    public id?: number,
    public numeroChapCompta?: number,
    public libelleChapCompta?: string,
    public sensChapCompta?: string,
    public soldeChapCompta?: number,
    public cumulMouvDebitChapCompta?: number,
    public cumulMouvCreditChapCompta?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public compteGenes?: ICompteGene[]
  ) {}
}
