import { IDetailPlanning } from 'app/shared/model/detail-planning.model';

export interface IEtatPlanning {
  id?: number;
  code?: string;
  libelle?: string;
  detailPlannings?: IDetailPlanning[];
}

export class EtatPlanning implements IEtatPlanning {
  constructor(public id?: number, public code?: string, public libelle?: string, public detailPlannings?: IDetailPlanning[]) {}
}
