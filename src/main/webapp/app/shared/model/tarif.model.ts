import { Moment } from 'moment';
import { ITypeSoins } from 'app/shared/model/type-soins.model';
import { ISousFamille } from 'app/shared/model/sous-famille.model';
import { IActeMedical } from 'app/shared/model/acte-medical.model';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';

export interface ITarif {
  id?: number;
  code?: string;
  libelle?: string;
  montant?: number;
  pourcentage?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typeSoins?: ITypeSoins;
  sousfamille?: ISousFamille;
  actemedical?: IActeMedical;
  categorieChambre?: ICatChambre;
}

export class Tarif implements ITarif {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public montant?: number,
    public pourcentage?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typeSoins?: ITypeSoins,
    public sousfamille?: ISousFamille,
    public actemedical?: IActeMedical,
    public categorieChambre?: ICatChambre
  ) {}
}
