import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FonctionnaliteDetailComponent } from 'app/entities/fonctionnalite/fonctionnalite-detail.component';
import { Fonctionnalite } from 'app/shared/model/fonctionnalite.model';

describe('Component Tests', () => {
  describe('Fonctionnalite Management Detail Component', () => {
    let comp: FonctionnaliteDetailComponent;
    let fixture: ComponentFixture<FonctionnaliteDetailComponent>;
    const route = ({ data: of({ fonctionnalite: new Fonctionnalite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FonctionnaliteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FonctionnaliteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FonctionnaliteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fonctionnalite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fonctionnalite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
