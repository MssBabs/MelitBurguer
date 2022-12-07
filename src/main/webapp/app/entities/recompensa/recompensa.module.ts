import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MelitBurguerSharedModule } from 'app/shared';
import {
  RecompensaComponent,
  RecompensaDetailComponent,
  RecompensaUpdateComponent,
  RecompensaDeletePopupComponent,
  RecompensaDeleteDialogComponent,
  recompensaRoute,
  recompensaPopupRoute
} from './';

const ENTITY_STATES = [...recompensaRoute, ...recompensaPopupRoute];

@NgModule({
  imports: [MelitBurguerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecompensaComponent,
    RecompensaDetailComponent,
    RecompensaUpdateComponent,
    RecompensaDeleteDialogComponent,
    RecompensaDeletePopupComponent
  ],
  entryComponents: [RecompensaComponent, RecompensaUpdateComponent, RecompensaDeleteDialogComponent, RecompensaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerRecompensaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
