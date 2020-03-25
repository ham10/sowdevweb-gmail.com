import { Moment } from 'moment';
import { ILit } from 'app/shared/model/lit.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IHospitalisation {
  id?: number;
  dateEntre?: Moment;
  dateSortie?: Moment;
  observation?: string;
  modeSortie?: string;
  objetPatient?: string;
  dateAccouchement?: Moment;
  dateOperation?: Moment;
  dateReveil?: Moment;
  poidsBebe?: number;
  tailleBebe?: number;
  poidsPlacenta?: number;
  genreBebe?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  lit?: ILit;
  patient?: IPatient;
}

export class Hospitalisation implements IHospitalisation {
  constructor(
    public id?: number,
    public dateEntre?: Moment,
    public dateSortie?: Moment,
    public observation?: string,
    public modeSortie?: string,
    public objetPatient?: string,
    public dateAccouchement?: Moment,
    public dateOperation?: Moment,
    public dateReveil?: Moment,
    public poidsBebe?: number,
    public tailleBebe?: number,
    public poidsPlacenta?: number,
    public genreBebe?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public lit?: ILit,
    public patient?: IPatient
  ) {}
}
