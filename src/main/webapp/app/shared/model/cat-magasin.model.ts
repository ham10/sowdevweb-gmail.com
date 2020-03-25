import { Moment } from 'moment';

export interface ICatMagasin {
  id?: number;
  codeCatMagasin?: string;
  libelleCatMagasin?: string;
  descriptionCatMagasin?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class CatMagasin implements ICatMagasin {
  constructor(
    public id?: number,
    public codeCatMagasin?: string,
    public libelleCatMagasin?: string,
    public descriptionCatMagasin?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
