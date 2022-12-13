import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';
import { ClienteRecompensaService } from './cliente-recompensa.service';
import { ClienteRecompensaComponent } from './cliente-recompensa.component';
import { ClienteRecompensaDetailComponent } from './cliente-recompensa-detail.component';
import { ClienteRecompensaUpdateComponent } from './cliente-recompensa-update.component';
import { ClienteRecompensaDeletePopupComponent } from './cliente-recompensa-delete-dialog.component';
import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';

@Injectable({ providedIn: 'root' })
export class ClienteRecompensaResolve implements Resolve<IClienteRecompensa> {
  constructor(private service: ClienteRecompensaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClienteRecompensa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ClienteRecompensa>) => response.ok),
        map((clienteRecompensa: HttpResponse<ClienteRecompensa>) => clienteRecompensa.body)
      );
    }
    return of(new ClienteRecompensa());
  }
}

export const clienteRecompensaRoute: Routes = [
  {
    path: '',
    component: ClienteRecompensaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.clienteRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClienteRecompensaDetailComponent,
    resolve: {
      clienteRecompensa: ClienteRecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.clienteRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClienteRecompensaUpdateComponent,
    resolve: {
      clienteRecompensa: ClienteRecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.clienteRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClienteRecompensaUpdateComponent,
    resolve: {
      clienteRecompensa: ClienteRecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.clienteRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const clienteRecompensaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClienteRecompensaDeletePopupComponent,
    resolve: {
      clienteRecompensa: ClienteRecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.clienteRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
