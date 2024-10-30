import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { taskRepositoryInstance } from "../repositories/repository-instances";
import { TaskUpdateProps } from "../data/props/task-update.props";

export const updateTask = async (
  taskUpdateProps: TaskUpdateProps
): Promise<Either<AppError, void>> => {
  return await taskRepositoryInstance.updateTask(taskUpdateProps);
};
