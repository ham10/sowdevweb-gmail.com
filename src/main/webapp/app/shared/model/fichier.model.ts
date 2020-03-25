import { Moment } from 'moment';

export interface IFichier {
  id?: number;
  cheminFichier?: string;
  formatFichier?: string;
  separateurFichier?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class Fichier implements IFichier {
  constructor(
    public id?: number,
    public cheminFichier?: string,
    public formatFichier?: string,
    public separateurFichier?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
