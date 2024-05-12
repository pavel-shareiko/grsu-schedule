import {Pagination} from "../../../core/types/pagination";

export type Faculty = {
  id: number;
  title: string;
}

export type FacultySearchItem = {
  id: number;
  title: string;
  createTimestamp: string;
}

export type FacultiesSearchFilter = {
  id?: number;
  title?: string;
}

export type FacultiesSearchResponse = {
  payload: FacultySearchItem[];
  pagination: Pagination;
}
