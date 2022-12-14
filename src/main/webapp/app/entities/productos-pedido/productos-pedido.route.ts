import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from './productos-pedido.service';
import { ProductosPedidoComponent } from './productos-pedido.component';
import { ProductosPedidoDetailComponent } from './productos-pedido-detail.component';
import { ProductosPedidoUpdateComponent } from './productos-pedido-update.component';
import { ProductosPedidoDeletePopupComponent } from './productos-pedido-delete-dialog.component';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';

@Injectable({ providedIn: 'root' })
export class ProductosPedidoResolve implements Resolve<IProductosPedido> {
  constructor(private service: ProductosPedidoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductosPedido> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductosPedido>) => response.ok),
        map((productosPedido: HttpResponse<ProductosPedido>) => productosPedido.body)
      );
    }
    return of(new ProductosPedido());
  }
}

export const productosPedidoRoute: Routes = [
  {
    path: '',
    component: ProductosPedidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_TRABAJADOR', 'ROLE_TRABAJADOR_COCINA', 'ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.productosPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductosPedidoDetailComponent,
    resolve: {
      productosPedido: ProductosPedidoResolve
    },
    data: {
      authorities: ['ROLE_TRABAJADOR', 'ROLE_TRABAJADOR_COCINA', 'ROLE_ADMIN'],
      pageTitle: 'melitBurguerApp.productosPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductosPedidoUpdateComponent,
    resolve: {
      productosPedido: ProductosPedidoResolve
    },
    data: {
      authorities: ['ROLE_TRABAJADOR', 'ROLE_ADMIN'],
      pageTitle: 'melitBurguerApp.productosPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductosPedidoUpdateComponent,
    resolve: {
      productosPedido: ProductosPedidoResolve
    },
    data: {
      authorities: ['ROLE_TRABAJADOR', 'ROLE_ADMIN'],
      pageTitle: 'melitBurguerApp.productosPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productosPedidoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductosPedidoDeletePopupComponent,
    resolve: {
      productosPedido: ProductosPedidoResolve
    },
    data: {
      authorities: ['ROLE_TRABAJADOR', 'ROLE_ADMIN'],
      pageTitle: 'melitBurguerApp.productosPedido.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
