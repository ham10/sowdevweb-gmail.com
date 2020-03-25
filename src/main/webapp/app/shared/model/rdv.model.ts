import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';
import { IPlanning } from 'app/shared/model/planning.model';
import { IEtatRdv } from 'app/shared/model/etat-rdv.model';

export interface IRDV {
  id?: number;
  numRdv?: string;
  dateRdv?: Moment;
  heureRdv?: Moment;
  patient?: IPatient;
  planning?: IPlanning;
  etatRDV?: IEtatRdv;
}

export class RDV implements IRDV {
  constructor(
    public id?: number,
    public numRdv?: string,
    public dateRdv?: Moment,
    public heureRdv?: Moment,
    public patient?: IPatient,
    public planning?: IPlanning,
    public etatRDV?: IEtatRdv
  ) {}
}
