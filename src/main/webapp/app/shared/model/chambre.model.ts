import { Moment } from 'moment';
import { IBoxe } from 'app/shared/model/boxe.model';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';

export interface IChambre {
  id?: number;
  numeroChambre?: number;
  postTelChambre?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  boxes?: IBoxe[];
  categorieChambre?: ICatChambre;
}

export class Chambre implements IChambre {
  constructor(
    public id?: number,
    public numeroChambre?: number,
    public postTelChambre?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public boxes?: IBoxe[],
    public categorieChambre?: ICatChambre
  ) {}
}
