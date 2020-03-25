import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InventaireService } from 'app/entities/inventaire/inventaire.service';
import { IInventaire, Inventaire } from 'app/shared/model/inventaire.model';

describe('Service Tests', () => {
  describe('Inventaire Service', () => {
    let injector: TestBed;
    let service: InventaireService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventaire;
    let expectedResult: IInventaire | IInventaire[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InventaireService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Inventaire(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Inventaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Inventaire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Inventaire', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            date: 'BBBBBB',
            quantiteEntrant: 1,
            quantiteInitiale: 1,
            quantiteSortant: 1,
            nombreSortant: 1,
            nombreLivraison: 1,
            nombreRetour: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Inventaire', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            date: 'BBBBBB',
            quantiteEntrant: 1,
            quantiteInitiale: 1,
            quantiteSortant: 1,
            nombreSortant: 1,
            nombreLivraison: 1,
            nombreRetour: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Inventaire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
