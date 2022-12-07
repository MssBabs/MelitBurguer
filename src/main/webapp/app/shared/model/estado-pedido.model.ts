export interface IEstadoPedido {
  id?: number;
  nombre?: string;
  descripcion?: string;
  pedidoId?: number;
}

export class EstadoPedido implements IEstadoPedido {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public pedidoId?: number) {}
}
