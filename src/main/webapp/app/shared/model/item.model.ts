import { Moment } from 'moment';
import { IFonctionnalite } from 'app/shared/model/fonctionnalite.model';
import { IModule } from 'app/shared/model/module.model';

export interface IItem {
  id?: number;
  libelleItem?: string;
  descriptionItem?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  fonctionnalites?: IFonctionnalite[];
  module?: IModule;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public libelleItem?: string,
    public descriptionItem?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public fonctionnalites?: IFonctionnalite[],
    public module?: IModule
  ) {}
}
