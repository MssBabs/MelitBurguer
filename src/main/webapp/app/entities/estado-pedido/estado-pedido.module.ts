import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  EstadoPedidoComponent,
  EstadoPedidoDetailComponent,
  EstadoPedidoUpdateComponent,
  EstadoPedidoDeletePopupComponent,
  EstadoPedidoDeleteDialogComponent,
  estadoPedidoRoute,
  estadoPedidoPopupRoute
} from './';

const ENTITY_STATES = [...estadoPedidoRoute, ...estadoPedidoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstadoPedidoComponent,
    EstadoPedidoDetailComponent,
    EstadoPedidoUpdateComponent,
    EstadoPedidoDeleteDialogComponent,
    EstadoPedidoDeletePopupComponent
  ],
  entryComponents: [
    EstadoPedidoComponent,
    EstadoPedidoUpdateComponent,
    EstadoPedidoDeleteDialogComponent,
    EstadoPedidoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerEstadoPedidoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
