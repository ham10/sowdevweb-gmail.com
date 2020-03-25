import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { IProduit } from 'app/shared/model/produit.model';
import { IFournisseur } from 'app/shared/model/fournisseur.model';

export interface IProdFournis {
  id?: number;
  stock?: string;
  nom?: string;
  ligneLivraisons?: ILigneLivraison[];
  ligneCommandes?: ILigneCommande[];
  produit?: IProduit;
  fournisseur?: IFournisseur;
}

export class ProdFournis implements IProdFournis {
  constructor(
    public id?: number,
    public stock?: string,
    public nom?: string,
    public ligneLivraisons?: ILigneLivraison[],
    public ligneCommandes?: ILigneCommande[],
    public produit?: IProduit,
    public fournisseur?: IFournisseur
  ) {}
}
