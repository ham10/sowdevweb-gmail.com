import { Moment } from 'moment';
import { ITarif } from 'app/shared/model/tarif.model';
import { IFamille } from 'app/shared/model/famille.model';

export interface ISousFamille {
  id?: number;
  code?: string;
  libelle?: string;
  description?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  tarifs?: ITarif[];
  famille?: IFamille;
}

export class SousFamille implements ISousFamille {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public description?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public tarifs?: ITarif[],
    public famille?: IFamille
  ) {}
}
