import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';
import { IFicheMedical, FicheMedical } from 'app/shared/model/fiche-medical.model';

describe('Service Tests', () => {
  describe('FicheMedical Service', () => {
    let injector: TestBed;
    let service: FicheMedicalService;
    let httpMock: HttpTestingController;
    let elemDefault: IFicheMedical;
    let expectedResult: IFicheMedical | IFicheMedical[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FicheMedicalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FicheMedical(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateConsultation: currentDate.format(DATE_FORMAT),
            dateProchainRV: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FicheMedical', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateConsultation: currentDate.format(DATE_FORMAT),
            dateProchainRV: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateConsultation: currentDate,
            dateProchainRV: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new FicheMedical()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FicheMedical', () => {
        const returnedFromService = Object.assign(
          {
            numeroFicheMedical: 'BBBBBB',
            dateConsultation: currentDate.format(DATE_FORMAT),
            facteurRisque: 'BBBBBB',
            regimeAlimentaire: 'BBBBBB',
            diagnostic: 'BBBBBB',
            recommandations: 'BBBBBB',
            commentaires: 'BBBBBB',
            dateProchainRV: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateConsultation: currentDate,
            dateProchainRV: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FicheMedical', () => {
        const returnedFromService = Object.assign(
          {
            numeroFicheMedical: 'BBBBBB',
            dateConsultation: currentDate.format(DATE_FORMAT),
            facteurRisque: 'BBBBBB',
            regimeAlimentaire: 'BBBBBB',
            diagnostic: 'BBBBBB',
            recommandations: 'BBBBBB',
            commentaires: 'BBBBBB',
            dateProchainRV: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateConsultation: currentDate,
            dateProchainRV: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FicheMedical', () => {
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
