import { Moment } from 'moment';

export interface ITypeQuestion {
  id?: number;
  code?: string;
  belleType?: string;
  multipliciteChoix?: number;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userUpdate?: number;
  userDelete?: number;
}

export class TypeQuestion implements ITypeQuestion {
  constructor(
    public id?: number,
    public code?: string,
    public belleType?: string,
    public multipliciteChoix?: number,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userUpdate?: number,
    public userDelete?: number
  ) {}
}
