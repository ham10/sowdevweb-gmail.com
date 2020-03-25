export interface IEtatRdv {
  id?: number;
  code?: string;
  libelle?: string;
}

export class EtatRdv implements IEtatRdv {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
