import { IProduit } from 'app/shared/model/produit.model';
import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

export interface IMouvement {
  id?: number;
  codeMvt?: string;
  libelleMvt?: string;
  stockEntrant?: number;
  stockSortant?: number;
  produit?: IProduit;
  typeMvtStock?: ITypeMvtStock;
}

export class Mouvement implements IMouvement {
  constructor(
    public id?: number,
    public codeMvt?: string,
    public libelleMvt?: string,
    public stockEntrant?: number,
    public stockSortant?: number,
    public produit?: IProduit,
    public typeMvtStock?: ITypeMvtStock
  ) {}
}
