import { Moment } from 'moment';
import { IDosMedical } from 'app/shared/model/dos-medical.model';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { ICompte } from 'app/shared/model/compte.model';
import { IGroupeSan } from 'app/shared/model/groupe-san.model';
import { ITypePatient } from 'app/shared/model/type-patient.model';
import { IDepartement } from 'app/shared/model/departement.model';

export interface IPatient {
  id?: number;
  codePatient?: string;
  nomPatient?: string;
  prenom?: string;
  adresse?: string;
  email?: string;
  dateNaissance?: Moment;
  genre?: string;
  photoContentType?: string;
  photo?: any;
  password?: string;
  telephone?: string;
  numeroPiece?: string;
  codeBarre?: string;
  entreprise?: string;
  ville?: string;
  quartier?: string;
  longitude?: string;
  latitude?: string;
  lieuNaissance?: string;
  fonctionPatient?: string;
  situationSociale?: string;
  solde?: number;
  cartePatient?: string;
  bloque?: boolean;
  dateValidite?: Moment;
  motifBlocage?: string;
  prenomPerePatient?: string;
  nomMerePatient?: string;
  prenomMerePatient?: string;
  motifAdmission?: string;
  personneAContacter?: string;
  adressePersAContacter?: string;
  telPersAContacter?: string;
  lienParentePersAContacter?: string;
  nomAccompagnant?: string;
  prenomAccompagnant?: string;
  telAccompagnant?: string;
  habitudeDeVie?: string;
  physioPathologique?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  dosMedicals?: IDosMedical[];
  hospitalisations?: IHospitalisation[];
  comptes?: ICompte[];
  groupeSanguin?: IGroupeSan;
  typePatient?: ITypePatient;
  departement?: IDepartement;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public codePatient?: string,
    public nomPatient?: string,
    public prenom?: string,
    public adresse?: string,
    public email?: string,
    public dateNaissance?: Moment,
    public genre?: string,
    public photoContentType?: string,
    public photo?: any,
    public password?: string,
    public telephone?: string,
    public numeroPiece?: string,
    public codeBarre?: string,
    public entreprise?: string,
    public ville?: string,
    public quartier?: string,
    public longitude?: string,
    public latitude?: string,
    public lieuNaissance?: string,
    public fonctionPatient?: string,
    public situationSociale?: string,
    public solde?: number,
    public cartePatient?: string,
    public bloque?: boolean,
    public dateValidite?: Moment,
    public motifBlocage?: string,
    public prenomPerePatient?: string,
    public nomMerePatient?: string,
    public prenomMerePatient?: string,
    public motifAdmission?: string,
    public personneAContacter?: string,
    public adressePersAContacter?: string,
    public telPersAContacter?: string,
    public lienParentePersAContacter?: string,
    public nomAccompagnant?: string,
    public prenomAccompagnant?: string,
    public telAccompagnant?: string,
    public habitudeDeVie?: string,
    public physioPathologique?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public dosMedicals?: IDosMedical[],
    public hospitalisations?: IHospitalisation[],
    public comptes?: ICompte[],
    public groupeSanguin?: IGroupeSan,
    public typePatient?: ITypePatient,
    public departement?: IDepartement
  ) {
    this.bloque = this.bloque || false;
  }
}
