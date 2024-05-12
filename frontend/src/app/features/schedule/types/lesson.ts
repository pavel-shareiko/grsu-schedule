import {Pagination} from "../../../core/types/pagination";
import {Time} from "@angular/common";

export type Lesson = {
  id: number;
  date: string;
  timeStart: Time;
  timeEnd: Time;
  teacherId: number;
  groupIds: number[];
  label: string;
  type: LessonType;
  title: string;
  address: Address;
  room: string;
}

export type LessonType = {
  id: number;
  title: string;
}

export type Address = {
  id: number;
  title: string;
  country: string;
  city: string;
  street: string;
  house: string;
  location: Coordinate;
}

export type Coordinate = {
  latitude: number;
  longitude: number;
}

export type LessonSearchItem = {
  id: number;
  date: string;
  timeStart: Time;
  timeEnd: Time;
  teachers: TeacherFullName[];
  groups: GroupTitle[];
  label: string;
  type: LessonType;
  title: string;
  address: Address;
  room: string;
}

export type TeacherFullName = {
  id: number;
  fullName: string;
}

export type GroupTitle = {
  id: number;
  title: string;
}

export type LessonSearchResponse = {
  pagination: Pagination;
  payload: LessonSearchItem[];
}
