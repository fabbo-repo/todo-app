import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { taskRepositoryInstance } from "../repositories/repository-instances";
import { TaskEntity } from "../data/entities/task.entity";

export const createTask = async (
  task: TaskEntity
): Promise<Either<AppError, void>> => {
  const taskEither = await taskRepositoryInstance.createTask(task);
  return taskEither.fold(
    (error) => {
      return Either.left(error);
    },
    () => {
      return Either.right(undefined);
    }
  );
};
