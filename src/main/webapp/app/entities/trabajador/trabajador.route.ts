import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Trabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorService } from './trabajador.service';
import { TrabajadorComponent } from './trabajador.component';
import { TrabajadorDetailComponent } from './trabajador-detail.component';
import { TrabajadorUpdateComponent } from './trabajador-update.component';
import { TrabajadorDeletePopupComponent } from './trabajador-delete-dialog.component';
import { ITrabajador } from 'app/shared/model/trabajador.model';

@Injectable({ providedIn: 'root' })
export class TrabajadorResolve implements Resolve<ITrabajador> {
  constructor(private service: TrabajadorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrabajador> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Trabajador>) => response.ok),
        map((trabajador: HttpResponse<Trabajador>) => trabajador.body)
      );
    }
    return of(new Trabajador());
  }
}

export const trabajadorRoute: Routes = [
  {
    path: '',
    component: TrabajadorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'melitBurguerApp.trabajador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrabajadorDetailComponent,
    resolve: {
      trabajador: TrabajadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.trabajador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrabajadorUpdateComponent,
    resolve: {
      trabajador: TrabajadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.trabajador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrabajadorUpdateComponent,
    resolve: {
      trabajador: TrabajadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.trabajador.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const trabajadorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TrabajadorDeletePopupComponent,
    resolve: {
      trabajador: TrabajadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'melitBurguerApp.trabajador.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
