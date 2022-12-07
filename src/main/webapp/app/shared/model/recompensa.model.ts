export interface IRecompensa {
  id?: number;
  nombre?: string;
  descripcion?: string;
  puntos?: number;
  clienteRecompensaId?: number;
}

export class Recompensa implements IRecompensa {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public puntos?: number,
    public clienteRecompensaId?: number
  ) {}
}
