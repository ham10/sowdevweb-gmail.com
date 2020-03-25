import { Moment } from 'moment';
import { IEcriture } from 'app/shared/model/ecriture.model';
import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface ICompte {
  id?: number;
  numeroCompte?: number;
  libelleCompte?: string;
  soldeCompte?: number;
  sensCompte?: string;
  cumulMouvDebit?: number;
  cumulMouvCredit?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: number;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  ecritures?: IEcriture[];
  compteGeneral?: ICompteGene;
  patient?: IPatient;
}

export class Compte implements ICompte {
  constructor(
    public id?: number,
    public numeroCompte?: number,
    public libelleCompte?: string,
    public soldeCompte?: number,
    public sensCompte?: string,
    public cumulMouvDebit?: number,
    public cumulMouvCredit?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: number,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public ecritures?: IEcriture[],
    public compteGeneral?: ICompteGene,
    public patient?: IPatient
  ) {}
}
