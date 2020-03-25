import { Moment } from 'moment';
import { IEvenement } from 'app/shared/model/evenement.model';

export interface IActivite {
  id?: number;
  dateActivite?: Moment;
  heureConnexionActivite?: string;
  heureDeConnexionActivite?: string;
  adresseIpActivite?: string;
  dateCreated?: Moment;
  userCreated?: number;
  adresseMac?: string;
  evenements?: IEvenement[];
}

export class Activite implements IActivite {
  constructor(
    public id?: number,
    public dateActivite?: Moment,
    public heureConnexionActivite?: string,
    public heureDeConnexionActivite?: string,
    public adresseIpActivite?: string,
    public dateCreated?: Moment,
    public userCreated?: number,
    public adresseMac?: string,
    public evenements?: IEvenement[]
  ) {}
}
