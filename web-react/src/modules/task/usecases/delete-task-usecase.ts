import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { taskRepositoryInstance } from "../repositories/repository-instances";

export const deleteTask = async (
  id: string
): Promise<Either<AppError, void>> => {
  return await taskRepositoryInstance.deleteTask(id);
};
