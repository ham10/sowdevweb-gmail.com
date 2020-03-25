import { IActivite } from 'app/shared/model/activite.model';

export interface IEvenement {
  id?: number;
  heure?: string;
  eventName?: string;
  description?: string;
  dateCreated?: string;
  userCreated?: number;
  userUpdated?: number;
  idEntity?: number;
  entityToucher?: string;
  activite?: IActivite;
}

export class Evenement implements IEvenement {
  constructor(
    public id?: number,
    public heure?: string,
    public eventName?: string,
    public description?: string,
    public dateCreated?: string,
    public userCreated?: number,
    public userUpdated?: number,
    public idEntity?: number,
    public entityToucher?: string,
    public activite?: IActivite
  ) {}
}
