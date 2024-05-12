import {Pagination} from "../../../core/types/pagination";

export type Faculty = {
  id: number;
  title: string;
}

export type FacultiesSearchFilter = {
  id?: number;
  title?: string;
}

export type FacultiesSearchResponse = {
  payload: Faculty[];
  pagination: Pagination;
}
