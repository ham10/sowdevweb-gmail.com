import { Moment } from 'moment';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { ITypeQuestion } from 'app/shared/model/type-question.model';

export interface IQuestion {
  id?: number;
  code?: string;
  libelle?: string;
  description?: string;
  obligatoire?: boolean;
  dateDeleted?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdate?: number;
  userDelete?: number;
  questionnaire?: IQuestionnaire;
  typeQuestion?: ITypeQuestion;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public description?: string,
    public obligatoire?: boolean,
    public dateDeleted?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdate?: number,
    public userDelete?: number,
    public questionnaire?: IQuestionnaire,
    public typeQuestion?: ITypeQuestion
  ) {
    this.obligatoire = this.obligatoire || false;
  }
}
