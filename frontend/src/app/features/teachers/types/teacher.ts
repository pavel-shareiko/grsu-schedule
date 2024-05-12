import {Pagination} from "../../../core/types/pagination";

export interface Teacher {
  id: number;
  name: string;
  surname: string;
  patronym: string;
  post: string;
  phone: string;
  descr: string;
  email: string;
  skype: string;
  createTimestamp: string;
  updateTimestamp: string;
}

export type TeacherSearchFilter = {
  id?: string;
  name?: string;
  surname?: string;
  patronym?: string;
  post?: string;
  phone?: string;
  descr?: string;
  email?: string;
  skype?: string;
  createTimestamp?: string;
};

export type TeacherSearchResponse = {
  payload: Teacher[];
  pagination: Pagination;
}
