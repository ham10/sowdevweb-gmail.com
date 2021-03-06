import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { MouvementDetailComponent } from 'app/entities/mouvement/mouvement-detail.component';
import { Mouvement } from 'app/shared/model/mouvement.model';

describe('Component Tests', () => {
  describe('Mouvement Management Detail Component', () => {
    let comp: MouvementDetailComponent;
    let fixture: ComponentFixture<MouvementDetailComponent>;
    const route = ({ data: of({ mouvement: new Mouvement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [MouvementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MouvementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MouvementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mouvement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mouvement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
