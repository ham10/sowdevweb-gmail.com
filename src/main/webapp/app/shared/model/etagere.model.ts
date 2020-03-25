import { IProduit } from 'app/shared/model/produit.model';
import { IRayon } from 'app/shared/model/rayon.model';

export interface IEtagere {
  id?: number;
  code?: string;
  libelle?: string;
  produits?: IProduit[];
  rayon?: IRayon;
}

export class Etagere implements IEtagere {
  constructor(public id?: number, public code?: string, public libelle?: string, public produits?: IProduit[], public rayon?: IRayon) {}
}
