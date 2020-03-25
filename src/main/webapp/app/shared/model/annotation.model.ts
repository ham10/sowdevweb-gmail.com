import { Moment } from 'moment';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';

export interface IAnnotation {
  id?: number;
  code?: string;
  note?: number;
  observation?: string;
  nbQuestions?: number;
  moyenne?: number;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdate?: number;
  userDelete?: number;
  questionnaire?: IQuestionnaire;
}

export class Annotation implements IAnnotation {
  constructor(
    public id?: number,
    public code?: string,
    public note?: number,
    public observation?: string,
    public nbQuestions?: number,
    public moyenne?: number,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdate?: number,
    public userDelete?: number,
    public questionnaire?: IQuestionnaire
  ) {}
}
