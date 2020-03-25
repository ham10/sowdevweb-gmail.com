import { IProduit } from 'app/shared/model/produit.model';

export interface ITypeCond {
  id?: number;
  code?: string;
  libelle?: string;
  produit?: IProduit;
}

export class TypeCond implements ITypeCond {
  constructor(public id?: number, public code?: string, public libelle?: string, public produit?: IProduit) {}
}
