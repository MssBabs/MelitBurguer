import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  ClienteRecompensaComponent,
  ClienteRecompensaDetailComponent,
  ClienteRecompensaUpdateComponent,
  ClienteRecompensaDeletePopupComponent,
  ClienteRecompensaDeleteDialogComponent,
  clienteRecompensaRoute,
  clienteRecompensaPopupRoute
} from './';

const ENTITY_STATES = [...clienteRecompensaRoute, ...clienteRecompensaPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteRecompensaComponent,
    ClienteRecompensaDetailComponent,
    ClienteRecompensaUpdateComponent,
    ClienteRecompensaDeleteDialogComponent,
    ClienteRecompensaDeletePopupComponent
  ],
  entryComponents: [
    ClienteRecompensaComponent,
    ClienteRecompensaUpdateComponent,
    ClienteRecompensaDeleteDialogComponent,
    ClienteRecompensaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerClienteRecompensaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
