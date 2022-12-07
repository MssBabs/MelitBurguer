import { IPedido } from 'app/shared/model/pedido.model';

export interface ITrabajador {
  id?: number;
  nombre?: string;
  apellido?: string;
  dni?: string;
  telefono?: number;
  correo?: string;
  pedidos?: IPedido[];
}

export class Trabajador implements ITrabajador {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public dni?: string,
    public telefono?: number,
    public correo?: string,
    public pedidos?: IPedido[]
  ) {}
}
