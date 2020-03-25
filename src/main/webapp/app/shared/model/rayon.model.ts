import { IEtagere } from 'app/shared/model/etagere.model';
import { IDepot } from 'app/shared/model/depot.model';

export interface IRayon {
  id?: number;
  codeRayon?: string;
  libelleRayon?: string;
  etageres?: IEtagere[];
  rayon?: IDepot;
}

export class Rayon implements IRayon {
  constructor(
    public id?: number,
    public codeRayon?: string,
    public libelleRayon?: string,
    public etageres?: IEtagere[],
    public rayon?: IDepot
  ) {}
}
