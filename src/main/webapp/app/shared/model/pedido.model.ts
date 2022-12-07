import { Moment } from 'moment';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';

export interface IPedido {
  id?: number;
  precioFinal?: number;
  fecha?: Moment;
  estadoPedidoId?: number;
  clienteId?: number;
  trabajadorId?: number;
  productosPedidos?: IProductosPedido[];
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public precioFinal?: number,
    public fecha?: Moment,
    public estadoPedidoId?: number,
    public clienteId?: number,
    public trabajadorId?: number,
    public productosPedidos?: IProductosPedido[]
  ) {}
}
