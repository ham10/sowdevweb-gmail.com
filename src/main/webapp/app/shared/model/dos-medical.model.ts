import { Moment } from 'moment';
import { IAntecedent } from 'app/shared/model/antecedent.model';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { IServices } from 'app/shared/model/services.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IDosMedical {
  id?: number;
  dateCreation?: Moment;
  numeroDossierDosMedical?: number;
  niveauDependance?: number;
  etatConscience?: number;
  etatCutane?: number;
  intoleranceMedic?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  antecedents?: IAntecedent[];
  ficheMedical?: IFicheMedical;
  serv?: IServices;
  patient?: IPatient;
}

export class DosMedical implements IDosMedical {
  constructor(
    public id?: number,
    public dateCreation?: Moment,
    public numeroDossierDosMedical?: number,
    public niveauDependance?: number,
    public etatConscience?: number,
    public etatCutane?: number,
    public intoleranceMedic?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public antecedents?: IAntecedent[],
    public ficheMedical?: IFicheMedical,
    public serv?: IServices,
    public patient?: IPatient
  ) {}
}
