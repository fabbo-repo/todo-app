import { PageEntity } from "../entities/page-entity";

export function mapPage<T, V>(
  page: PageEntity<T>,
  mapper: (prop: T) => V
): PageEntity<V> {
  const results: V[] = [];
  page.results.forEach((response) => {
    results.push(mapper(response));
  });
  return {
    totalElements: page.totalElements,
    pageSize: page.pageSize,
    pageIndex: page.pageIndex,
    firstPage: page.firstPage,
    lastPage: page.lastPage,
    results: results,
  };
}
