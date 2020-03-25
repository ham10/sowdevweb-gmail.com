import { Moment } from 'moment';
import { IPlanning } from 'app/shared/model/planning.model';
import { IEtatPlanning } from 'app/shared/model/etat-planning.model';

export interface IDetailPlanning {
  id?: number;
  titre?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  planning?: IPlanning;
  etatPlanning?: IEtatPlanning;
}

export class DetailPlanning implements IDetailPlanning {
  constructor(
    public id?: number,
    public titre?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public planning?: IPlanning,
    public etatPlanning?: IEtatPlanning
  ) {}
}
