import { Moment } from 'moment';
import { ITypePlateau } from 'app/shared/model/type-plateau.model';

export interface IPlateau {
  id?: number;
  libellePlateau?: string;
  descriptionPlateau?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typePlateau?: ITypePlateau;
}

export class Plateau implements IPlateau {
  constructor(
    public id?: number,
    public libellePlateau?: string,
    public descriptionPlateau?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typePlateau?: ITypePlateau
  ) {}
}
