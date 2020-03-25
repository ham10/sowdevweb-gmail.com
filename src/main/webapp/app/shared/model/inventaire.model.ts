import { IProduit } from 'app/shared/model/produit.model';

export interface IInventaire {
  id?: number;
  code?: string;
  date?: string;
  quantiteEntrant?: number;
  quantiteInitiale?: number;
  quantiteSortant?: number;
  nombreSortant?: number;
  nombreLivraison?: number;
  nombreRetour?: number;
  produit?: IProduit;
}

export class Inventaire implements IInventaire {
  constructor(
    public id?: number,
    public code?: string,
    public date?: string,
    public quantiteEntrant?: number,
    public quantiteInitiale?: number,
    public quantiteSortant?: number,
    public nombreSortant?: number,
    public nombreLivraison?: number,
    public nombreRetour?: number,
    public produit?: IProduit
  ) {}
}
