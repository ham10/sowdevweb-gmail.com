import { Moment } from 'moment';
import { IItem } from 'app/shared/model/item.model';

export interface IModule {
  id?: number;
  libelleModule?: string;
  descriptionModule?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  items?: IItem[];
}

export class Module implements IModule {
  constructor(
    public id?: number,
    public libelleModule?: string,
    public descriptionModule?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public items?: IItem[]
  ) {}
}
