import { Moment } from 'moment';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';
import { IDosMedical } from 'app/shared/model/dos-medical.model';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { IPlat } from 'app/shared/model/plat.model';
import { ITypeServices } from 'app/shared/model/type-services.model';
import { IDeptServices } from 'app/shared/model/dept-services.model';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { ICompteGene } from 'app/shared/model/compte-gene.model';

export interface IServices {
  id?: number;
  code?: string;
  libelle?: string;
  description?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  catChambres?: ICatChambre[];
  dosMedicals?: IDosMedical[];
  bonDeCommandes?: IBonDeCommande[];
  plats?: IPlat[];
  typeServices?: ITypeServices;
  deptServices?: IDeptServices;
  specialite?: ISpecialite;
  compteGeneral?: ICompteGene;
}

export class Services implements IServices {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public description?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public catChambres?: ICatChambre[],
    public dosMedicals?: IDosMedical[],
    public bonDeCommandes?: IBonDeCommande[],
    public plats?: IPlat[],
    public typeServices?: ITypeServices,
    public deptServices?: IDeptServices,
    public specialite?: ISpecialite,
    public compteGeneral?: ICompteGene
  ) {}
}
