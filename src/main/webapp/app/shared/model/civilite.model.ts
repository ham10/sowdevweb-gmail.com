export interface ICivilite {
  id?: number;
  libelleCivilite?: string;
  intituleCivilite?: string;
}

export class Civilite implements ICivilite {
  constructor(public id?: number, public libelleCivilite?: string, public intituleCivilite?: string) {}
}
