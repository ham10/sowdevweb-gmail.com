import { Moment } from 'moment';
import { IItem } from 'app/shared/model/item.model';

export interface IFonctionnalite {
  id?: number;
  libelleFonctionnalite?: string;
  descriptionFonctionnalite?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  item?: IItem;
}

export class Fonctionnalite implements IFonctionnalite {
  constructor(
    public id?: number,
    public libelleFonctionnalite?: string,
    public descriptionFonctionnalite?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public item?: IItem
  ) {}
}
