export interface IProductosPedido {
  id?: number;
  precioProducto?: number;
  productosId?: number;
  pedidoId?: number;
}

export class ProductosPedido implements IProductosPedido {
  constructor(public id?: number, public precioProducto?: number, public productosId?: number, public pedidoId?: number) {}
}
