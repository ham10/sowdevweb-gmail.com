import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { DosMedicalDetailComponent } from 'app/entities/dos-medical/dos-medical-detail.component';
import { DosMedical } from 'app/shared/model/dos-medical.model';

describe('Component Tests', () => {
  describe('DosMedical Management Detail Component', () => {
    let comp: DosMedicalDetailComponent;
    let fixture: ComponentFixture<DosMedicalDetailComponent>;
    const route = ({ data: of({ dosMedical: new DosMedical(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [DosMedicalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DosMedicalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DosMedicalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dosMedical on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dosMedical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
