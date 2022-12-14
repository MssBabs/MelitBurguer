import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import { ProductoDetailPopupComponent } from './producto-detail-dialog.component';
import { ProductoUpdatePopupComponent } from './producto-update-dialog.component';
import {
  ProductoComponent,
  ProductoDetailComponent,
  ProductoUpdateComponent,
  ProductoDeletePopupComponent,
  ProductoDeleteDialogComponent,
  productoRoute,
  productoPopupRoute
} from './';

const ENTITY_STATES = [...productoRoute, ...productoPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoComponent,
    ProductoDetailComponent,
    ProductoUpdateComponent,
    ProductoDeleteDialogComponent,
    ProductoDeletePopupComponent,
    ProductoDetailPopupComponent,
    ProductoUpdatePopupComponent
  ],
  entryComponents: [
    ProductoComponent,
    ProductoUpdateComponent,
    ProductoDeleteDialogComponent,
    ProductoDeletePopupComponent,
    ProductoDetailPopupComponent,
    ProductoUpdatePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerProductoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
