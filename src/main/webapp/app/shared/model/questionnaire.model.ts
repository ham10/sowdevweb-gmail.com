import { Moment } from 'moment';

export interface IQuestionnaire {
  id?: number;
  code?: string;
  libelle?: string;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdate?: number;
  userDelete?: number;
}

export class Questionnaire implements IQuestionnaire {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdate?: number,
    public userDelete?: number
  ) {}
}
