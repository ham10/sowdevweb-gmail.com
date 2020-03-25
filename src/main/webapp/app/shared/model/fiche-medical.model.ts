import { Moment } from 'moment';
import { IDosMedical } from 'app/shared/model/dos-medical.model';
import { IResultatActe } from 'app/shared/model/resultat-acte.model';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { IVaccin } from 'app/shared/model/vaccin.model';
import { IMedecin } from 'app/shared/model/medecin.model';
import { ITypeConstante } from 'app/shared/model/type-constante.model';

export interface IFicheMedical {
  id?: number;
  numeroFicheMedical?: string;
  dateConsultation?: Moment;
  facteurRisque?: string;
  regimeAlimentaire?: string;
  diagnostic?: string;
  recommandations?: string;
  commentaires?: string;
  dateProchainRV?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  dossierMedicals?: IDosMedical[];
  resultatActes?: IResultatActe[];
  ordonnances?: IOrdonnance[];
  vaccins?: IVaccin[];
  medecin?: IMedecin;
  typeConstantes?: ITypeConstante[];
}

export class FicheMedical implements IFicheMedical {
  constructor(
    public id?: number,
    public numeroFicheMedical?: string,
    public dateConsultation?: Moment,
    public facteurRisque?: string,
    public regimeAlimentaire?: string,
    public diagnostic?: string,
    public recommandations?: string,
    public commentaires?: string,
    public dateProchainRV?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public dossierMedicals?: IDosMedical[],
    public resultatActes?: IResultatActe[],
    public ordonnances?: IOrdonnance[],
    public vaccins?: IVaccin[],
    public medecin?: IMedecin,
    public typeConstantes?: ITypeConstante[]
  ) {}
}
