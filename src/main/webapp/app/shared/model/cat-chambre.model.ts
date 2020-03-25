import { Moment } from 'moment';
import { IChambre } from 'app/shared/model/chambre.model';
import { ITarif } from 'app/shared/model/tarif.model';
import { IServices } from 'app/shared/model/services.model';

export interface ICatChambre {
  id?: number;
  libelleCatChambre?: string;
  descriptionCatChambre?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  chambres?: IChambre[];
  tarifs?: ITarif[];
  serv?: IServices;
}

export class CatChambre implements ICatChambre {
  constructor(
    public id?: number,
    public libelleCatChambre?: string,
    public descriptionCatChambre?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public chambres?: IChambre[],
    public tarifs?: ITarif[],
    public serv?: IServices
  ) {}
}
