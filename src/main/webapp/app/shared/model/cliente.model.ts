export interface ICliente {
  id?: number;
  nombre?: string;
  apellido?: string;
  telefono?: number;
  correo?: string;
  puntos?: number;
  pedidoId?: number;
  clienteRecompensaId?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public telefono?: number,
    public correo?: string,
    public puntos?: number,
    public pedidoId?: number,
    public clienteRecompensaId?: number
  ) {}
}
