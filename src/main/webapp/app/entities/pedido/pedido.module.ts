import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  PedidoComponent,
  PedidoDetailComponent,
  PedidoUpdateComponent,
  PedidoDeletePopupComponent,
  PedidoDeleteDialogComponent,
  pedidoRoute,
  pedidoPopupRoute
} from './';
import { PedidoProductoPopupComponent } from './pedido-producto-dialog.component';
import { PedidoProductoDeletePopupComponent } from './pedido-producto-delete-dialog.component';

const ENTITY_STATES = [...pedidoRoute, ...pedidoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PedidoComponent,
    PedidoDetailComponent,
    PedidoProductoPopupComponent,
    PedidoUpdateComponent,
    PedidoDeleteDialogComponent,
    PedidoDeletePopupComponent,
    PedidoProductoDeletePopupComponent
  ],
  entryComponents: [
    PedidoComponent,
    PedidoProductoPopupComponent,
    PedidoUpdateComponent,
    PedidoDeleteDialogComponent,
    PedidoDeletePopupComponent,
    PedidoProductoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerPedidoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
