import { ITypeCond } from 'app/shared/model/type-cond.model';
import { IMouvement } from 'app/shared/model/mouvement.model';
import { IInventaire } from 'app/shared/model/inventaire.model';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { IEtagere } from 'app/shared/model/etagere.model';
import { ITypeProd } from 'app/shared/model/type-prod.model';

export interface IProduit {
  id?: number;
  code?: string;
  libelle?: string;
  quantiteStock?: number;
  stockProvisoire?: number;
  tva?: boolean;
  prixVenteUnitaire?: number;
  codeBarre?: string;
  seuil?: number;
  typeConds?: ITypeCond[];
  mouvements?: IMouvement[];
  inventaires?: IInventaire[];
  prodFournis?: IProdFournis[];
  etagere?: IEtagere;
  typeproduit?: ITypeProd;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public quantiteStock?: number,
    public stockProvisoire?: number,
    public tva?: boolean,
    public prixVenteUnitaire?: number,
    public codeBarre?: string,
    public seuil?: number,
    public typeConds?: ITypeCond[],
    public mouvements?: IMouvement[],
    public inventaires?: IInventaire[],
    public prodFournis?: IProdFournis[],
    public etagere?: IEtagere,
    public typeproduit?: ITypeProd
  ) {
    this.tva = this.tva || false;
  }
}
