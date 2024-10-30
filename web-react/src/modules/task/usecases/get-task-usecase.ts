import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { TaskEntity } from "../data/entities/task.entity";
import { taskRepositoryInstance } from "../repositories/repository-instances";

export const getTask = async (
  id: string
): Promise<Either<AppError, TaskEntity>> => {
  return await taskRepositoryInstance.getTask(id);
};
