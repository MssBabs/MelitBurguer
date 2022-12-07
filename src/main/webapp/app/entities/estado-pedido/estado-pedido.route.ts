import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstadoPedido } from 'app/shared/model/estado-pedido.model';
import { EstadoPedidoService } from './estado-pedido.service';
import { EstadoPedidoComponent } from './estado-pedido.component';
import { EstadoPedidoDetailComponent } from './estado-pedido-detail.component';
import { EstadoPedidoUpdateComponent } from './estado-pedido-update.component';
import { EstadoPedidoDeletePopupComponent } from './estado-pedido-delete-dialog.component';
import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';

@Injectable({ providedIn: 'root' })
export class EstadoPedidoResolve implements Resolve<IEstadoPedido> {
  constructor(private service: EstadoPedidoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstadoPedido> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstadoPedido>) => response.ok),
        map((estadoPedido: HttpResponse<EstadoPedido>) => estadoPedido.body)
      );
    }
    return of(new EstadoPedido());
  }
}

export const estadoPedidoRoute: Routes = [
  {
    path: '',
    component: EstadoPedidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.estadoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoPedidoDetailComponent,
    resolve: {
      estadoPedido: EstadoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.estadoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoPedidoUpdateComponent,
    resolve: {
      estadoPedido: EstadoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.estadoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoPedidoUpdateComponent,
    resolve: {
      estadoPedido: EstadoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.estadoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estadoPedidoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstadoPedidoDeletePopupComponent,
    resolve: {
      estadoPedido: EstadoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.estadoPedido.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
