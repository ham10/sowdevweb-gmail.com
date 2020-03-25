import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { HospitalisationService } from 'app/entities/hospitalisation/hospitalisation.service';
import { IHospitalisation, Hospitalisation } from 'app/shared/model/hospitalisation.model';

describe('Service Tests', () => {
  describe('Hospitalisation Service', () => {
    let injector: TestBed;
    let service: HospitalisationService;
    let httpMock: HttpTestingController;
    let elemDefault: IHospitalisation;
    let expectedResult: IHospitalisation | IHospitalisation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HospitalisationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Hospitalisation(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0,
        'AAAAAAA',
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
            dateEntre: currentDate.format(DATE_FORMAT),
            dateSortie: currentDate.format(DATE_FORMAT),
            dateAccouchement: currentDate.format(DATE_TIME_FORMAT),
            dateOperation: currentDate.format(DATE_FORMAT),
            dateReveil: currentDate.format(DATE_FORMAT),
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

      it('should create a Hospitalisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEntre: currentDate.format(DATE_FORMAT),
            dateSortie: currentDate.format(DATE_FORMAT),
            dateAccouchement: currentDate.format(DATE_TIME_FORMAT),
            dateOperation: currentDate.format(DATE_FORMAT),
            dateReveil: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEntre: currentDate,
            dateSortie: currentDate,
            dateAccouchement: currentDate,
            dateOperation: currentDate,
            dateReveil: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Hospitalisation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Hospitalisation', () => {
        const returnedFromService = Object.assign(
          {
            dateEntre: currentDate.format(DATE_FORMAT),
            dateSortie: currentDate.format(DATE_FORMAT),
            observation: 'BBBBBB',
            modeSortie: 'BBBBBB',
            objetPatient: 'BBBBBB',
            dateAccouchement: currentDate.format(DATE_TIME_FORMAT),
            dateOperation: currentDate.format(DATE_FORMAT),
            dateReveil: currentDate.format(DATE_FORMAT),
            poidsBebe: 1,
            tailleBebe: 1,
            poidsPlacenta: 1,
            genreBebe: 'BBBBBB',
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
            dateEntre: currentDate,
            dateSortie: currentDate,
            dateAccouchement: currentDate,
            dateOperation: currentDate,
            dateReveil: currentDate,
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

      it('should return a list of Hospitalisation', () => {
        const returnedFromService = Object.assign(
          {
            dateEntre: currentDate.format(DATE_FORMAT),
            dateSortie: currentDate.format(DATE_FORMAT),
            observation: 'BBBBBB',
            modeSortie: 'BBBBBB',
            objetPatient: 'BBBBBB',
            dateAccouchement: currentDate.format(DATE_TIME_FORMAT),
            dateOperation: currentDate.format(DATE_FORMAT),
            dateReveil: currentDate.format(DATE_FORMAT),
            poidsBebe: 1,
            tailleBebe: 1,
            poidsPlacenta: 1,
            genreBebe: 'BBBBBB',
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
            dateEntre: currentDate,
            dateSortie: currentDate,
            dateAccouchement: currentDate,
            dateOperation: currentDate,
            dateReveil: currentDate,
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

      it('should delete a Hospitalisation', () => {
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
