import {Pagination} from "../../../core/types/pagination";

export type Lesson = {
  id: number;
  date: string;
  timeStart: string;
  timeEnd: string;
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

export type LessonSearchResponse = {
  pagination: Pagination;
  payload: Lesson[];
}
