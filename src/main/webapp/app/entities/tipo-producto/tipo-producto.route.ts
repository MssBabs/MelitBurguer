import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';
import { TipoProductoComponent } from './tipo-producto.component';
import { TipoProductoDetailComponent } from './tipo-producto-detail.component';
import { TipoProductoUpdateComponent } from './tipo-producto-update.component';
import { TipoProductoDeletePopupComponent } from './tipo-producto-delete-dialog.component';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

@Injectable({ providedIn: 'root' })
export class TipoProductoResolve implements Resolve<ITipoProducto> {
  constructor(private service: TipoProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoProducto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoProducto>) => response.ok),
        map((tipoProducto: HttpResponse<TipoProducto>) => tipoProducto.body)
      );
    }
    return of(new TipoProducto());
  }
}

export const tipoProductoRoute: Routes = [
  {
    path: '',
    component: TipoProductoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE', 'ROLE_TRABAJADOR'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.tipoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoProductoDetailComponent,
    resolve: {
      tipoProducto: TipoProductoResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE', 'ROLE_TRABAJADOR'],
      pageTitle: 'melitBurguerApp.tipoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoProductoUpdateComponent,
    resolve: {
      tipoProducto: TipoProductoResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.tipoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoProductoUpdateComponent,
    resolve: {
      tipoProducto: TipoProductoResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.tipoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoProductoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoProductoDeletePopupComponent,
    resolve: {
      tipoProducto: TipoProductoResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.tipoProducto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
