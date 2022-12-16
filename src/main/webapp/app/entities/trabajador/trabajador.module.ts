import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  TrabajadorComponent,
  TrabajadorDetailComponent,
  TrabajadorUpdateComponent,
  TrabajadorDeletePopupComponent,
  TrabajadorDeleteDialogComponent,
  trabajadorRoute,
  trabajadorPopupRoute
} from './';
import { TrabajadorDetailPopupComponent } from './trabajador-detail-dialog.component';
import { TrabajadorUpdatePopupComponent } from './trabajador-update-dialog.component';

const ENTITY_STATES = [...trabajadorRoute, ...trabajadorPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TrabajadorComponent,
    TrabajadorDetailComponent,
    TrabajadorDetailPopupComponent,
    TrabajadorUpdateComponent,
    TrabajadorUpdatePopupComponent,
    TrabajadorDeleteDialogComponent,
    TrabajadorDeletePopupComponent
  ],
  entryComponents: [
    TrabajadorComponent,
    TrabajadorUpdateComponent,
    TrabajadorDeleteDialogComponent,
    TrabajadorDeletePopupComponent,
    TrabajadorDetailPopupComponent,
    TrabajadorUpdatePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerTrabajadorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
