import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CalendrierService } from 'app/entities/calendrier/calendrier.service';
import { ICalendrier, Calendrier } from 'app/shared/model/calendrier.model';

describe('Service Tests', () => {
  describe('Calendrier Service', () => {
    let injector: TestBed;
    let service: CalendrierService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalendrier;
    let expectedResult: ICalendrier | ICalendrier[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CalendrierService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Calendrier(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureDebut: currentDate.format(DATE_FORMAT),
            heureFin: currentDate.format(DATE_FORMAT),
            dateDebut: currentDate.format(DATE_FORMAT),
            dateFin: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Calendrier', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureDebut: currentDate.format(DATE_FORMAT),
            heureFin: currentDate.format(DATE_FORMAT),
            dateDebut: currentDate.format(DATE_FORMAT),
            dateFin: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
            dateDebut: currentDate,
            dateFin: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate,
            userCreated: currentDate
          },
          returnedFromService
        );

        service.create(new Calendrier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Calendrier', () => {
        const returnedFromService = Object.assign(
          {
            libelleCalendrier: 'BBBBBB',
            heureDebut: currentDate.format(DATE_FORMAT),
            heureFin: currentDate.format(DATE_FORMAT),
            dateDebut: currentDate.format(DATE_FORMAT),
            dateFin: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: currentDate.format(DATE_FORMAT),
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
            dateDebut: currentDate,
            dateFin: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate,
            userCreated: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Calendrier', () => {
        const returnedFromService = Object.assign(
          {
            libelleCalendrier: 'BBBBBB',
            heureDebut: currentDate.format(DATE_FORMAT),
            heureFin: currentDate.format(DATE_FORMAT),
            dateDebut: currentDate.format(DATE_FORMAT),
            dateFin: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: currentDate.format(DATE_FORMAT),
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebut: currentDate,
            heureFin: currentDate,
            dateDebut: currentDate,
            dateFin: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate,
            userCreated: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Calendrier', () => {
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
