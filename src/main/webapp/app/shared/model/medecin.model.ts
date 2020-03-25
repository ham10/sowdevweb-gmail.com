import { Moment } from 'moment';

export interface IMedecin {
  id?: number;
  nomMedecin?: string;
  prenom?: string;
  adresse?: string;
  email?: string;
  dateNaissance?: Moment;
  genreMedecin?: string;
  nationalite?: string;
  telephone?: string;
  anciennete?: number;
  numeroPiece?: number;
  gradeMedecin?: string;
  specialite?: string;
  dateEmbauche?: Moment;
  dateValidite?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class Medecin implements IMedecin {
  constructor(
    public id?: number,
    public nomMedecin?: string,
    public prenom?: string,
    public adresse?: string,
    public email?: string,
    public dateNaissance?: Moment,
    public genreMedecin?: string,
    public nationalite?: string,
    public telephone?: string,
    public anciennete?: number,
    public numeroPiece?: number,
    public gradeMedecin?: string,
    public specialite?: string,
    public dateEmbauche?: Moment,
    public dateValidite?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
