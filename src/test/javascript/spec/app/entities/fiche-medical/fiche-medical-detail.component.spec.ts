import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FicheMedicalDetailComponent } from 'app/entities/fiche-medical/fiche-medical-detail.component';
import { FicheMedical } from 'app/shared/model/fiche-medical.model';

describe('Component Tests', () => {
  describe('FicheMedical Management Detail Component', () => {
    let comp: FicheMedicalDetailComponent;
    let fixture: ComponentFixture<FicheMedicalDetailComponent>;
    const route = ({ data: of({ ficheMedical: new FicheMedical(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FicheMedicalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FicheMedicalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FicheMedicalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ficheMedical on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ficheMedical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
