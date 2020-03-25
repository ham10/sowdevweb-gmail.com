export interface ISitMat {
  id?: number;
  codeSitMat?: string;
  libelleSitMat?: string;
}

export class SitMat implements ISitMat {
  constructor(public id?: number, public codeSitMat?: string, public libelleSitMat?: string) {}
}
