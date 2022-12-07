import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MelitBurguerSharedLibsModule, MelitBurguerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [MelitBurguerSharedLibsModule, MelitBurguerSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MelitBurguerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MelitBurguerSharedModule {
  static forRoot() {
    return {
      ngModule: MelitBurguerSharedModule
    };
  }
}
