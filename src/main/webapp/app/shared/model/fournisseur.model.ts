import { Moment } from 'moment';
import { IOffre } from 'app/shared/model/offre.model';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';

export interface IFournisseur {
  id?: number;
  nom?: string;
  statut?: string;
  raisonSociale?: string;
  adresse?: string;
  telephone?: string;
  ninea?: string;
  rc?: string;
  ville?: string;
  email?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  offres?: IOffre[];
  prodFournis?: IProdFournis[];
}

export class Fournisseur implements IFournisseur {
  constructor(
    public id?: number,
    public nom?: string,
    public statut?: string,
    public raisonSociale?: string,
    public adresse?: string,
    public telephone?: string,
    public ninea?: string,
    public rc?: string,
    public ville?: string,
    public email?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public offres?: IOffre[],
    public prodFournis?: IProdFournis[]
  ) {}
}
