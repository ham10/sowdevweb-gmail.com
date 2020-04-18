import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import * as moment from 'moment';
import {DATE_TIME_FORMAT} from 'app/shared/constants/input.constants';

import {IRDV, RDV} from 'app/shared/model/rdv.model';
import {RDVService} from './rdv.service';
import {IPatient} from 'app/shared/model/patient.model';
import {PatientService} from 'app/entities/patient/patient.service';
import {IPlanning} from 'app/shared/model/planning.model';
import {PlanningService} from 'app/entities/planning/planning.service';
import {IEtatRdv} from 'app/shared/model/etat-rdv.model';
import {EtatRdvService} from 'app/entities/etat-rdv/etat-rdv.service';
import {  isSameDay, isSameMonth} from 'date-fns';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView , DAYS_OF_WEEK} from 'angular-calendar';
// import {Moment} from "moment";
import {DetailPlanningService} from "app/entities/detail-planning/detail-planning.service";
import {IDetailPlanning} from "app/shared/model/detail-planning.model";
import {MedecinService} from "app/entities/medecin/medecin.service";
import {Moment} from "moment";
// import {IMedecin} from "app/shared/model/medecin.model";


type SelectableEntity = IPatient | IPlanning | IEtatRdv;
const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3'
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF'
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA'
  }
};
@Component({
  selector: 'jhi-rdv-update',
  templateUrl: './rdv-update.component.html'
})
export class RDVUpdateComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any> | null = null;

  view: CalendarView = CalendarView.Month;
  activeDayIsOpen = true;
  CalendarView = CalendarView;

  // locale: string = 'fr';

  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;

  weekendDays: number[] = [DAYS_OF_WEEK.FRIDAY, DAYS_OF_WEEK.SATURDAY];



  viewDate: Date = new Date();
  modalData: {
    action: string;
    event: CalendarEvent;
  } | null = null;

  isSaving = false;
  patients: IPatient[] = [];
  plannings: IPlanning[] = [];
  etatrdvs: IEtatRdv[] = [];
  patient: IPatient | null = null;
  dateRdvDp: any;
  searchForm = this.fb.group({
    nameSearch: ['', Validators.required]
  });
  editForm = this.fb.group({
    id: [],
    numRdv: [],
    dateRdv: [],
    heureRdv: [],
    patient: [],
    planning: [],
    etatRDV: []
  });

  refresh: Subject<any> = new Subject();
  actions: CalendarEventAction[] = [
    {
      label: '<i class="fa fa-fw fa-pencil"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      }
    },
    {
      label: '<i class="fa fa-fw fa-times"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter(iEvent => iEvent !== event);
        this.handleEvent('Deleted', event);
      }
    }
  ];
  details: IDetailPlanning[] = [];

  events: CalendarEvent[] = [
    // {
    //   start: subDays(startOfDay(new Date()), 1),
    //   end: addDays(new Date(), 1),
    //   title: 'A 3 day event',
    //   color: colors.red,
    //   actions: this.actions,
    //   allDay: true,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true
    //   },
    //   draggable: true
    // },
    // {
    //   start: startOfDay(new Date()),
    //   title: 'An event with no end date',
    //   color: colors.yellow,
    //   actions: this.actions
    // },
    // {
    //   start: subDays(endOfMonth(new Date()), 3),
    //   end: addDays(endOfMonth(new Date()), 3),
    //   title: 'A long event that spans 2 months',
    //   color: colors.blue,
    //   allDay: true
    // },
    // {
    //   start: addHours(startOfDay(new Date()), 2),
    //   end: addHours(new Date(), 2),
    //   title: 'A draggable and resizable event',
    //   color: colors.yellow,
    //   actions: this.actions,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true
    //   },
    //   draggable: true
    // }
  ];


  constructor(
    protected rDVService: RDVService,
    protected patientService: PatientService,
    protected planningService: PlanningService,
    protected etatRdvService: EtatRdvService,
    protected activatedRoute: ActivatedRoute,
    private detailPlanningService: DetailPlanningService,
    private medecinService: MedecinService,
    private modal: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rDV }) => {
      if (!rDV.id) {
        const today = moment().startOf('day');
        rDV.heureRdv = today;
      }

      this.updateForm(rDV);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));

      this.loadPlanning();

      this.etatRdvService.query().subscribe((res: HttpResponse<IEtatRdv[]>) => (this.etatrdvs = res.body || []));
    });


  }

  loadPlanning(): void {
    this.detailPlanningService.findAllByMedecin(152).subscribe((res) => {
      this.details = res.body || [];
      if(this.details.length > 0){

        this.details.map((d) => {
          const dateDebut : Moment = d.dateDebut as Moment;
          const dateFin : Moment = d.dateFin as Moment;
          const  titre: string = d.titre as string;
          const t: CalendarEvent = {
              id: 'ev'+d.id,
              start: dateDebut,
              end: dateFin,
              title: titre,
              color: colors.red,
              actions: this.actions,
              allDay: true,
              resizable: {
                beforeStart: true,
                afterEnd: true
              },
              draggable: true
            };
          this.events.push(t);
          this.refresh.next();
        });

      }
    });




  }
  searchPatient(): void{
    if(this.searchForm.invalid){
      return ;
    }else {
      const codePatient = this.searchForm.value.nameSearch;
      this.patientService.findByCode(codePatient).subscribe((res: HttpResponse<IPatient>) => this.patient = res.body);
    }

  }
  updateForm(rDV: IRDV): void {
    this.editForm.patchValue({
      id: rDV.id,
      numRdv: rDV.numRdv,
      dateRdv: rDV.dateRdv,
      heureRdv: rDV.heureRdv ? rDV.heureRdv.format(DATE_TIME_FORMAT) : null,
      patient: rDV.patient,
      planning: rDV.planning,
      etatRDV: rDV.etatRDV
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rDV = this.createFromForm();
    if (rDV.id !== undefined) {
      this.subscribeToSaveResponse(this.rDVService.update(rDV));
    } else {
      this.subscribeToSaveResponse(this.rDVService.create(rDV));
    }
  }

  private createFromForm(): IRDV {
    return {
      ...new RDV(),
      id: this.editForm.get(['id'])!.value,
      numRdv: '',
      dateRdv: this.editForm.get(['dateRdv'])!.value,
      heureRdv: this.editForm.get(['heureRdv'])!.value ? moment(this.editForm.get(['heureRdv'])!.value, DATE_TIME_FORMAT) : undefined,
      patient: this.editForm.get(['patient'])!.value,
      planning: this.editForm.get(['planning'])!.value,
      etatRDV: this.editForm.get(['etatRDV'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRDV>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }


  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map(iEvent => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd
        };
      }
      return iEvent;
    });
     this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
    this.modal.open(this.modalContent, { size: 'lg' });
  }

  // addEvent(): void {
  //   this.events = [
  //     ...this.events,
  //     {
  //       title: 'New event',
  //       start: startOfDay(new Date()),
  //       end: endOfDay(new Date()),
  //       color: colors.red,
  //       draggable: true,
  //       resizable: {
  //         beforeStart: true,
  //         afterEnd: true
  //       },
  //
  //     }
  //   ];
  // }


  deleteEvent(eventToDelete: CalendarEvent): void {
    this.events = this.events.filter(event => event !== eventToDelete);
  }

  setView(view: CalendarView): void {
    this.view = view;
  }

  closeOpenMonthViewDay(): void {
    this.activeDayIsOpen = false;
  }
}
