import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/operation.model';
import { INatureOp } from 'app/shared/model/nature-op.model';

export interface ISchemaCompta {
  id?: number;
  code?: number;
  libelle?: string;
  sens?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  operation?: IOperation;
  natureOperation?: INatureOp;
}

export class SchemaCompta implements ISchemaCompta {
  constructor(
    public id?: number,
    public code?: number,
    public libelle?: string,
    public sens?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public operation?: IOperation,
    public natureOperation?: INatureOp
  ) {}
}
