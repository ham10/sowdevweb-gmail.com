import { Moment } from 'moment';
import { ISchemaCompta } from 'app/shared/model/schema-compta.model';

export interface INatureOp {
  id?: number;
  numero?: number;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  schemaComptas?: ISchemaCompta[];
}

export class NatureOp implements INatureOp {
  constructor(
    public id?: number,
    public numero?: number,
    public libelle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public schemaComptas?: ISchemaCompta[]
  ) {}
}
