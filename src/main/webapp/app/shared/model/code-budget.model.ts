import { Moment } from 'moment';
import { ICompteGene } from 'app/shared/model/compte-gene.model';

export interface ICodeBudget {
  id?: number;
  codeCodeBudget?: number;
  libelleCodeBudget?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  compteGeneral?: ICompteGene;
}

export class CodeBudget implements ICodeBudget {
  constructor(
    public id?: number,
    public codeCodeBudget?: number,
    public libelleCodeBudget?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public compteGeneral?: ICompteGene
  ) {}
}
