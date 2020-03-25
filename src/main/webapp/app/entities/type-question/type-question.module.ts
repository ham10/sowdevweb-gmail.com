import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HpdSharedModule } from 'app/shared/shared.module';
import { TypeQuestionComponent } from './type-question.component';
import { TypeQuestionDetailComponent } from './type-question-detail.component';
import { TypeQuestionUpdateComponent } from './type-question-update.component';
import { TypeQuestionDeleteDialogComponent } from './type-question-delete-dialog.component';
import { typeQuestionRoute } from './type-question.route';

@NgModule({
  imports: [HpdSharedModule, RouterModule.forChild(typeQuestionRoute)],
  declarations: [TypeQuestionComponent, TypeQuestionDetailComponent, TypeQuestionUpdateComponent, TypeQuestionDeleteDialogComponent],
  entryComponents: [TypeQuestionDeleteDialogComponent]
})
export class HpdTypeQuestionModule {}
