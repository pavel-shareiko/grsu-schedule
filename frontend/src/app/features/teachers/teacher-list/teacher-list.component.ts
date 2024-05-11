import {Component, OnInit, ViewChild} from '@angular/core';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TeacherService} from "../service/teacher.service";
import {Teacher, TeacherSearchFilter} from "../types/teacher";
import {DatePipe, NgIf} from "@angular/common";
import {
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {MatOption, MatSelect} from "@angular/material/select";
import {Router} from "@angular/router";

@Component({
  selector: 'app-teacher-list',
  standalone: true,
  imports: [
    MatFormField,
    MatInput,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCellDef,
    MatCell,
    MatLabel,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef,
    MatPaginator,
    ReactiveFormsModule,
    DatePipe,
    MatExpansionPanel,
    MatExpansionPanelTitle,
    MatExpansionPanelHeader,
    MatExpansionPanelDescription,
    MatIcon,
    MatSelect,
    MatOption,
    NgIf
  ],
  templateUrl: './teacher-list.component.html',
  styleUrl: './teacher-list.component.scss'
})
export class TeacherListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fullName', 'post', 'email', 'createTimestamp'];
  dataSource!: MatTableDataSource<any>;
  pageSize: number = 10;
  pageIndex: number = 0;
  totalElements: number = 0;

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;

  public form!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private teacherService: TeacherService,
              private router: Router) {
  }

  ngOnInit() {
    this.loadTeachers();

    this.form = this.formBuilder.group({
      id: [null],
      name: [null],
      surname: [null],
      patronym: [null],
      email: [null],
      post: [null]
    });
  }

  loadTeachers() {
    const filter = this.form?.value as TeacherSearchFilter;
    this.teacherService.getTeachers(filter, this.pageIndex, this.pageSize).subscribe(response => {
      this.dataSource = new MatTableDataSource(response.payload);
      this.totalElements = response.pagination.totalElements;

      this.dataSource.filterPredicate = (data: any, filter: string) => {
        const dataStr = JSON.stringify(data).toLowerCase();
        const filterStr = filter.trim().toLowerCase();
        return dataStr.indexOf(filterStr) != -1;
      };
    });
  }

  onPageChange($event: PageEvent) {
    this.pageIndex = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.loadTeachers();
  }

  applyFilter() {
    this.paginator.firstPage();
    this.loadTeachers();
  }

  onRowClick(row: Teacher) {
    this.router.navigate(['/teachers', row.id]);
  }
}
