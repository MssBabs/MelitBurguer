import { Moment } from 'moment';

export interface IClienteRecompensa {
  id?: number;
  fecha?: Moment;
  clienteId?: number;
  recompensaId?: number;
}

export class ClienteRecompensa implements IClienteRecompensa {
  constructor(public id?: number, public fecha?: Moment, public clienteId?: number, public recompensaId?: number) {}
}
