import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AnnotationService } from 'app/entities/annotation/annotation.service';
import { IAnnotation, Annotation } from 'app/shared/model/annotation.model';

describe('Service Tests', () => {
  describe('Annotation Service', () => {
    let injector: TestBed;
    let service: AnnotationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnnotation;
    let expectedResult: IAnnotation | IAnnotation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnnotationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Annotation(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, currentDate, currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Annotation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Annotation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Annotation', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            note: 1,
            observation: 'BBBBBB',
            nbQuestions: 1,
            moyenne: 1,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT),
            userCreated: 1,
            userUpdate: 1,
            userDelete: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
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

      it('should return a list of Annotation', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            note: 1,
            observation: 'BBBBBB',
            nbQuestions: 1,
            moyenne: 1,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT),
            userCreated: 1,
            userUpdate: 1,
            userDelete: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
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

      it('should delete a Annotation', () => {
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
