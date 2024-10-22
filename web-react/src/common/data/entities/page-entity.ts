export interface PageEntity<T> {
  totalElements: number;
  pageSize: number;
  pageIndex: number;
  firstPage: number;
  lastPage: number;
  results: T[];
}
