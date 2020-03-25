import { Moment } from 'moment';
import { IQuestion } from 'app/shared/model/question.model';

export interface IReponse {
  id?: number;
  code?: string;
  reponse?: string;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdate?: number;
  userDelete?: number;
  question?: IQuestion;
}

export class Reponse implements IReponse {
  constructor(
    public id?: number,
    public code?: string,
    public reponse?: string,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdate?: number,
    public userDelete?: number,
    public question?: IQuestion
  ) {}
}
