import { Moment } from 'moment';

export interface ITypePiece {
  id?: number;
  codeTypePiece?: string;
  libelleTypePiece?: string;
  descriptionTypePiece?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypePiece implements ITypePiece {
  constructor(
    public id?: number,
    public codeTypePiece?: string,
    public libelleTypePiece?: string,
    public descriptionTypePiece?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
