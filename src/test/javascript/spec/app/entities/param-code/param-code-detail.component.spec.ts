import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamCodeDetailComponent } from 'app/entities/param-code/param-code-detail.component';
import { ParamCode } from 'app/shared/model/param-code.model';

describe('Component Tests', () => {
  describe('ParamCode Management Detail Component', () => {
    let comp: ParamCodeDetailComponent;
    let fixture: ComponentFixture<ParamCodeDetailComponent>;
    const route = ({ data: of({ paramCode: new ParamCode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamCodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParamCodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParamCodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paramCode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paramCode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
