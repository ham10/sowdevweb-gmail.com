import { Moment } from 'moment';
import { IImmo } from 'app/shared/model/immo.model';
import { IEcriture } from 'app/shared/model/ecriture.model';
import { ISchemaCompta } from 'app/shared/model/schema-compta.model';
import { IEtatOperation } from 'app/shared/model/etat-operation.model';
import { ICaisse } from 'app/shared/model/caisse.model';
import { IFacture } from 'app/shared/model/facture.model';

export interface IOperation {
  id?: number;
  libelle?: string;
  date?: Moment;
  montant?: number;
  taxe?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  immos?: IImmo[];
  ecritures?: IEcriture[];
  schemaComptable?: ISchemaCompta;
  etatOperation?: IEtatOperation;
  caisse?: ICaisse;
  operation?: IFacture;
}

export class Operation implements IOperation {
  constructor(
    public id?: number,
    public libelle?: string,
    public date?: Moment,
    public montant?: number,
    public taxe?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public immos?: IImmo[],
    public ecritures?: IEcriture[],
    public schemaComptable?: ISchemaCompta,
    public etatOperation?: IEtatOperation,
    public caisse?: ICaisse,
    public operation?: IFacture
  ) {}
}
