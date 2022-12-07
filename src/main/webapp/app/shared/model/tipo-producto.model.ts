import { IProducto } from 'app/shared/model/producto.model';

export interface ITipoProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  productos?: IProducto[];
}

export class TipoProducto implements ITipoProducto {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public productos?: IProducto[]) {}
}
