import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EcritureDetailComponent } from 'app/entities/ecriture/ecriture-detail.component';
import { Ecriture } from 'app/shared/model/ecriture.model';

describe('Component Tests', () => {
  describe('Ecriture Management Detail Component', () => {
    let comp: EcritureDetailComponent;
    let fixture: ComponentFixture<EcritureDetailComponent>;
    const route = ({ data: of({ ecriture: new Ecriture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EcritureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EcritureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcritureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ecriture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ecriture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
