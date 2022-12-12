import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  ClienteComponent,
  ClienteDetailComponent,
  ClienteUpdateComponent,
  ClienteDeletePopupComponent,
  ClienteDeleteDialogComponent,
  clienteRoute,
  clientePopupRoute
} from './';
import { ClienteDetailPopupComponent } from './cliente-detail-dialog.component';
import { ClienteUpdatePopupComponent } from './cliente-update-dialog.component';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteComponent,
    ClienteDetailComponent,
    ClienteDetailPopupComponent,
    ClienteUpdateComponent,
    ClienteUpdatePopupComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent
  ],
  entryComponents: [
    ClienteComponent,
    ClienteDetailPopupComponent,
    ClienteUpdateComponent,
    ClienteUpdatePopupComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerClienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
