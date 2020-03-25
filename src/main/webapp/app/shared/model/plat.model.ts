import { Moment } from 'moment';
import { ITypePlat } from 'app/shared/model/type-plat.model';
import { IServices } from 'app/shared/model/services.model';

export interface IPlat {
  id?: number;
  quantite?: number;
  typeRepas?: string;
  date?: Moment;
  typePlat?: ITypePlat;
  serv?: IServices;
}

export class Plat implements IPlat {
  constructor(
    public id?: number,
    public quantite?: number,
    public typeRepas?: string,
    public date?: Moment,
    public typePlat?: ITypePlat,
    public serv?: IServices
  ) {}
}
