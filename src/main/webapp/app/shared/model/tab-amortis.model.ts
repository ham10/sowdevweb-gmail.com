import { Moment } from 'moment';
import { IImmo } from 'app/shared/model/immo.model';

export interface ITabAmortis {
  id?: number;
  libelle?: string;
  montant?: number;
  date?: Moment;
  etat?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  immos?: IImmo[];
}

export class TabAmortis implements ITabAmortis {
  constructor(
    public id?: number,
    public libelle?: string,
    public montant?: number,
    public date?: Moment,
    public etat?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public immos?: IImmo[]
  ) {}
}
