import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { NatureOpDetailComponent } from 'app/entities/nature-op/nature-op-detail.component';
import { NatureOp } from 'app/shared/model/nature-op.model';

describe('Component Tests', () => {
  describe('NatureOp Management Detail Component', () => {
    let comp: NatureOpDetailComponent;
    let fixture: ComponentFixture<NatureOpDetailComponent>;
    const route = ({ data: of({ natureOp: new NatureOp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [NatureOpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NatureOpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureOpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load natureOp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.natureOp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
