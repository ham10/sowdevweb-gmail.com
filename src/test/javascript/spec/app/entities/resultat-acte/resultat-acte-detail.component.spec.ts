import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ResultatActeDetailComponent } from 'app/entities/resultat-acte/resultat-acte-detail.component';
import { ResultatActe } from 'app/shared/model/resultat-acte.model';

describe('Component Tests', () => {
  describe('ResultatActe Management Detail Component', () => {
    let comp: ResultatActeDetailComponent;
    let fixture: ComponentFixture<ResultatActeDetailComponent>;
    const route = ({ data: of({ resultatActe: new ResultatActe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ResultatActeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultatActeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultatActeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultatActe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultatActe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
