import { Moment } from 'moment';
import { IJour } from 'app/shared/model/jour.model';

export interface IHoraireCon {
  id?: number;
  heureDebutHC?: Moment;
  heureFinHC?: Moment;
  dateDebutHC?: Moment;
  dateFinHC?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  jour?: IJour;
}

export class HoraireCon implements IHoraireCon {
  constructor(
    public id?: number,
    public heureDebutHC?: Moment,
    public heureFinHC?: Moment,
    public dateDebutHC?: Moment,
    public dateFinHC?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public jour?: IJour
  ) {}
}
