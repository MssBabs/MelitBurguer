import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tipo-producto',
        loadChildren: './tipo-producto/tipo-producto.module#MelitBurguerTipoProductoModule'
      },
      {
        path: 'producto',
        loadChildren: './producto/producto.module#MelitBurguerProductoModule'
      },
      {
        path: 'productos-pedido',
        loadChildren: './productos-pedido/productos-pedido.module#MelitBurguerProductosPedidoModule'
      },
      {
        path: 'estado-pedido',
        loadChildren: './estado-pedido/estado-pedido.module#MelitBurguerEstadoPedidoModule'
      },
      {
        path: 'pedido',
        loadChildren: './pedido/pedido.module#MelitBurguerPedidoModule'
      },
      {
        path: 'trabajador',
        loadChildren: './trabajador/trabajador.module#MelitBurguerTrabajadorModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#MelitBurguerClienteModule'
      },
      {
        path: 'cliente-recompensa',
        loadChildren: './cliente-recompensa/cliente-recompensa.module#MelitBurguerClienteRecompensaModule'
      },
      {
        path: 'recompensa',
        loadChildren: './recompensa/recompensa.module#MelitBurguerRecompensaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerEntityModule {}
