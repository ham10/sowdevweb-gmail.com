import { Moment } from 'moment';
import { ICompteGene } from 'app/shared/model/compte-gene.model';

export interface IBanque {
  id?: number;
  codeBanque?: string;
  ribBanque?: string;
  libelleBanque?: string;
  descriptionBanque?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  compteGeneral?: ICompteGene;
}

export class Banque implements IBanque {
  constructor(
    public id?: number,
    public codeBanque?: string,
    public ribBanque?: string,
    public libelleBanque?: string,
    public descriptionBanque?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public compteGeneral?: ICompteGene
  ) {}
}
