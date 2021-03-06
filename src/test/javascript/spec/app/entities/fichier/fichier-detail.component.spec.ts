import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FichierDetailComponent } from 'app/entities/fichier/fichier-detail.component';
import { Fichier } from 'app/shared/model/fichier.model';

describe('Component Tests', () => {
  describe('Fichier Management Detail Component', () => {
    let comp: FichierDetailComponent;
    let fixture: ComponentFixture<FichierDetailComponent>;
    const route = ({ data: of({ fichier: new Fichier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FichierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fichier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fichier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
