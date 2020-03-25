export interface ITypePlanning {
  id?: number;
  code?: string;
  libelle?: string;
}

export class TypePlanning implements ITypePlanning {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
