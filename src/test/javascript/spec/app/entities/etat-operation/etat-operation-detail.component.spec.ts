import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatOperationDetailComponent } from 'app/entities/etat-operation/etat-operation-detail.component';
import { EtatOperation } from 'app/shared/model/etat-operation.model';

describe('Component Tests', () => {
  describe('EtatOperation Management Detail Component', () => {
    let comp: EtatOperationDetailComponent;
    let fixture: ComponentFixture<EtatOperationDetailComponent>;
    const route = ({ data: of({ etatOperation: new EtatOperation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatOperationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatOperationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatOperationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatOperation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatOperation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
