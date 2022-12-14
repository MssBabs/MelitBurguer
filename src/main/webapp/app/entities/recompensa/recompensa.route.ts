import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recompensa } from 'app/shared/model/recompensa.model';
import { RecompensaService } from './recompensa.service';
import { RecompensaComponent } from './recompensa.component';
import { RecompensaDetailComponent } from './recompensa-detail.component';
import { RecompensaUpdateComponent } from './recompensa-update.component';
import { RecompensaDeletePopupComponent } from './recompensa-delete-dialog.component';
import { IRecompensa } from 'app/shared/model/recompensa.model';

@Injectable({ providedIn: 'root' })
export class RecompensaResolve implements Resolve<IRecompensa> {
  constructor(private service: RecompensaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecompensa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Recompensa>) => response.ok),
        map((recompensa: HttpResponse<Recompensa>) => recompensa.body)
      );
    }
    return of(new Recompensa());
  }
}

export const recompensaRoute: Routes = [
  {
    path: '',
    component: RecompensaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.recompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecompensaDetailComponent,
    resolve: {
      recompensa: RecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.recompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecompensaUpdateComponent,
    resolve: {
      recompensa: RecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.recompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecompensaUpdateComponent,
    resolve: {
      recompensa: RecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.recompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const recompensaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RecompensaDeletePopupComponent,
    resolve: {
      recompensa: RecompensaResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TRABAJADOR_JEFE'],
      pageTitle: 'melitBurguerApp.recompensa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
