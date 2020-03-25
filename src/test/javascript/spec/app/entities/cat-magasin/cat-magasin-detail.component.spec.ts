import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatMagasinDetailComponent } from 'app/entities/cat-magasin/cat-magasin-detail.component';
import { CatMagasin } from 'app/shared/model/cat-magasin.model';

describe('Component Tests', () => {
  describe('CatMagasin Management Detail Component', () => {
    let comp: CatMagasinDetailComponent;
    let fixture: ComponentFixture<CatMagasinDetailComponent>;
    const route = ({ data: of({ catMagasin: new CatMagasin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatMagasinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CatMagasinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatMagasinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catMagasin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catMagasin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
