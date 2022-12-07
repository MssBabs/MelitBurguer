export interface IProductosPedido {
  id?: number;
  precioTotal?: number;
  productosId?: number;
  pedidoId?: number;
}

export class ProductosPedido implements IProductosPedido {
  constructor(public id?: number, public precioTotal?: number, public productosId?: number, public pedidoId?: number) {}
}
