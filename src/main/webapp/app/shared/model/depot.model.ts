import { IRayon } from 'app/shared/model/rayon.model';

export interface IDepot {
  id?: number;
  codeDepot?: number;
  libelleDepot?: string;
  rayons?: IRayon[];
}

export class Depot implements IDepot {
  constructor(public id?: number, public codeDepot?: number, public libelleDepot?: string, public rayons?: IRayon[]) {}
}
