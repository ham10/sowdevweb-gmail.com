import { Moment } from 'moment';
import { ISousFamille } from 'app/shared/model/sous-famille.model';
import { ITypeTarif } from 'app/shared/model/type-tarif.model';

export interface IFamille {
  id?: number;
  code?: string;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  sousFamilles?: ISousFamille[];
  typeTarif?: ITypeTarif;
}

export class Famille implements IFamille {
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
    public sousFamilles?: ISousFamille[],
    public typeTarif?: ITypeTarif
  ) {}
}
