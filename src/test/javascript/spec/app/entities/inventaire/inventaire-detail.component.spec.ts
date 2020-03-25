import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { InventaireDetailComponent } from 'app/entities/inventaire/inventaire-detail.component';
import { Inventaire } from 'app/shared/model/inventaire.model';

describe('Component Tests', () => {
  describe('Inventaire Management Detail Component', () => {
    let comp: InventaireDetailComponent;
    let fixture: ComponentFixture<InventaireDetailComponent>;
    const route = ({ data: of({ inventaire: new Inventaire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [InventaireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
