import { IProductosPedido } from 'app/shared/model/productos-pedido.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  tipoProductoId?: number;
  productosPedidos?: IProductosPedido[];
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public precio?: number,
    public tipoProductoId?: number,
    public productosPedidos?: IProductosPedido[]
  ) {}
}
