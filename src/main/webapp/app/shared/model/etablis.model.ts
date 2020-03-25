import { Moment } from 'moment';
import { IDepartement } from 'app/shared/model/departement.model';

export interface IEtablis {
  id?: number;
  codeEtabl?: string;
  raisonSocialeEtabl?: string;
  adresseEtabl?: string;
  telephoneEtabl?: number;
  nineaEtabl?: string;
  rcEtabl?: string;
  emailEtabl?: string;
  description?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  departement?: IDepartement;
}

export class Etablis implements IEtablis {
  constructor(
    public id?: number,
    public codeEtabl?: string,
    public raisonSocialeEtabl?: string,
    public adresseEtabl?: string,
    public telephoneEtabl?: number,
    public nineaEtabl?: string,
    public rcEtabl?: string,
    public emailEtabl?: string,
    public description?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public departement?: IDepartement
  ) {}
}
