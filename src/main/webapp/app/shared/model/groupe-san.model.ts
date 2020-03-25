import { Moment } from 'moment';

export interface IGroupeSan {
  id?: number;
  codeGroupeSan?: number;
  libelleGroupeSan?: string;
  descriptionGroupeSan?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class GroupeSan implements IGroupeSan {
  constructor(
    public id?: number,
    public codeGroupeSan?: number,
    public libelleGroupeSan?: string,
    public descriptionGroupeSan?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
