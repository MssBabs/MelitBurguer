import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  TipoProductoComponent,
  TipoProductoDetailComponent,
  TipoProductoUpdateComponent,
  TipoProductoDeletePopupComponent,
  TipoProductoDeleteDialogComponent,
  tipoProductoRoute,
  tipoProductoPopupRoute
} from './';
import { TipoProductoDetailPopupComponent } from './tipo-producto-detail-dialog.component';
import { TipoProductoUpdatePopupComponent } from './tipo-producto-update-dialog.component';

const ENTITY_STATES = [...tipoProductoRoute, ...tipoProductoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoProductoComponent,
    TipoProductoDetailComponent,
    TipoProductoDetailPopupComponent,
    TipoProductoUpdateComponent,
    TipoProductoUpdatePopupComponent,
    TipoProductoDeleteDialogComponent,
    TipoProductoDeletePopupComponent
  ],
  entryComponents: [
    TipoProductoComponent,
    TipoProductoUpdateComponent,
    TipoProductoUpdatePopupComponent,
    TipoProductoDeleteDialogComponent,
    TipoProductoDetailPopupComponent,
    TipoProductoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerTipoProductoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
