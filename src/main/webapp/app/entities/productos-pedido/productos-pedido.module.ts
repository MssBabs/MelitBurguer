import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  ProductosPedidoComponent,
  ProductosPedidoDetailComponent,
  ProductosPedidoUpdateComponent,
  ProductosPedidoDeletePopupComponent,
  ProductosPedidoDeleteDialogComponent,
  productosPedidoRoute,
  productosPedidoPopupRoute
} from './';

const ENTITY_STATES = [...productosPedidoRoute, ...productosPedidoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductosPedidoComponent,
    ProductosPedidoDetailComponent,
    ProductosPedidoUpdateComponent,
    ProductosPedidoDeleteDialogComponent,
    ProductosPedidoDeletePopupComponent
  ],
  entryComponents: [
    ProductosPedidoComponent,
    ProductosPedidoUpdateComponent,
    ProductosPedidoDeleteDialogComponent,
    ProductosPedidoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerProductosPedidoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
