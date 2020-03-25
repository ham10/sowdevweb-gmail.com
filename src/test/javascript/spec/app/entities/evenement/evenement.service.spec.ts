import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EvenementService } from 'app/entities/evenement/evenement.service';
import { IEvenement, Evenement } from 'app/shared/model/evenement.model';

describe('Service Tests', () => {
  describe('Evenement Service', () => {
    let injector: TestBed;
    let service: EvenementService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvenement;
    let expectedResult: IEvenement | IEvenement[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EvenementService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Evenement(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Evenement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Evenement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Evenement', () => {
        const returnedFromService = Object.assign(
          {
            heure: 'BBBBBB',
            eventName: 'BBBBBB',
            description: 'BBBBBB',
            dateCreated: 'BBBBBB',
            userCreated: 1,
            userUpdated: 1,
            idEntity: 1,
            entityToucher: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Evenement', () => {
        const returnedFromService = Object.assign(
          {
            heure: 'BBBBBB',
            eventName: 'BBBBBB',
            description: 'BBBBBB',
            dateCreated: 'BBBBBB',
            userCreated: 1,
            userUpdated: 1,
            idEntity: 1,
            entityToucher: 'BBBBBB'
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

      it('should delete a Evenement', () => {
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
