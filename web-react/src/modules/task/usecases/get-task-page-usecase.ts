import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { PageEntity } from "../../../common/data/entities/page-entity";
import { TaskEntity } from "../data/entities/task.entity";
import { taskRepositoryInstance } from "../repositories/repository-instances";

export const getTaskPage = async (
  pageNum: number
): Promise<Either<AppError, PageEntity<TaskEntity>>> => {
  return await taskRepositoryInstance.getTaskPage({
    pageNum: pageNum,
    pageSize: 10,
  });
};
