import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { NgbdModalContent } from './modal-añadir-productos-en-pedido.component';
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

const ENTITY_STATES = [...pedidoRoute, ...pedidoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PedidoComponent,
    PedidoDetailComponent,
    PedidoUpdateComponent,
    PedidoDeleteDialogComponent,
    PedidoDeletePopupComponent,
    NgbdModalContent
  ],
  entryComponents: [PedidoComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, PedidoDeletePopupComponent, NgbdModalContent],
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
