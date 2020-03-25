export interface IEtatPlanning {
  id?: number;
  code?: string;
  libelle?: string;
}

export class EtatPlanning implements IEtatPlanning {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
