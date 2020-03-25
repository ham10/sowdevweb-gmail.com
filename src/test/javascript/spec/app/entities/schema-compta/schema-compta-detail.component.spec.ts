import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { SchemaComptaDetailComponent } from 'app/entities/schema-compta/schema-compta-detail.component';
import { SchemaCompta } from 'app/shared/model/schema-compta.model';

describe('Component Tests', () => {
  describe('SchemaCompta Management Detail Component', () => {
    let comp: SchemaComptaDetailComponent;
    let fixture: ComponentFixture<SchemaComptaDetailComponent>;
    const route = ({ data: of({ schemaCompta: new SchemaCompta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [SchemaComptaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SchemaComptaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchemaComptaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schemaCompta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schemaCompta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
