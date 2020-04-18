import { Moment } from 'moment';
import { IDetailPlanning } from 'app/shared/model/detail-planning.model';
import { IMedecin } from 'app/shared/model/medecin.model';
import { ITypePlanning } from 'app/shared/model/type-planning.model';

export interface IPlanning {
  id?: number;
  num?: string;
  libelle?: string;
  dateCreated?: Moment;
  detailPlannings?: IDetailPlanning[];
  medecin?: IMedecin;
  typePlanning?: ITypePlanning;
}

export class Planning implements IPlanning {
  constructor(
    public id?: number,
    public num?: string,
    public libelle?: string,
    public dateCreated?: Moment,
    public detailPlannings?: IDetailPlanning[],
    public medecin?: IMedecin,
    public typePlanning?: ITypePlanning
  ) {}
}
