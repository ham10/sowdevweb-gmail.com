import { Moment } from 'moment';
import { IFamille } from 'app/shared/model/famille.model';

export interface ITypeTarif {
  id?: number;
  code?: string;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  familles?: IFamille[];
}

export class TypeTarif implements ITypeTarif {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public familles?: IFamille[]
  ) {}
}
