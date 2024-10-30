import { Either } from "../../../common/data/either";
import { PageEntity } from "../../../common/data/entities/page-entity";
import { AppError } from "../../../common/data/errors/app-error";
import { mapPage } from "../../../common/data/mappers/page-mapper";
import { PageProps } from "../../../common/data/props/page-props";
import HttpService from "../../../common/services/http/http-service";
import { TASK_API_PATH } from "../../auth/data/constants/task-api-path-constants";
import { TaskPatchRequestRestDto } from "../data/dtos/task-patch-request-rest.dto";
import { TaskPostRequestRestDto } from "../data/dtos/task-post-request-rest.dto";
import { TaskResponseRestDto } from "../data/dtos/task-response-rest.dto";
import { TaskEntity } from "../data/entities/task.entity";
import {
  taskEntityToTaskPostRequestRestDto,
  taskResponseRestDtoToEntity,
  taskUpdatePropsToPatchRequestRestDto,
} from "../data/mappers/task.mapper";
import { TaskUpdateProps } from "../data/props/task-update.props";

export interface TaskRepository {
  getTask: (id: string) => Promise<Either<AppError, TaskEntity>>;

  getTaskPage: (
    pageProp: PageProps
  ) => Promise<Either<AppError, PageEntity<TaskEntity>>>;

  createTask: (task: TaskEntity) => Promise<Either<AppError, TaskEntity>>;

  updateTask: (
    taskUpdateProp: TaskUpdateProps
  ) => Promise<Either<AppError, void>>;

  deleteTask: (id: string) => Promise<Either<AppError, void>>;
}

export const taskRepository = (httpService: HttpService): TaskRepository => ({
  getTask: async (id: string): Promise<Either<AppError, TaskEntity>> => {
    const taskEitherResponse =
      await httpService.getRequest<TaskResponseRestDto>(
        TASK_API_PATH + "/" + id
      );
    return taskEitherResponse.fold(
      (error) => Either.left(error),
      (response) => Either.right(taskResponseRestDtoToEntity(response))
    );
  },

  getTaskPage: async (
    pageProp: PageProps
  ): Promise<Either<AppError, PageEntity<TaskEntity>>> => {
    const taskPageEitherResponse = await httpService.getRequest<
      PageEntity<TaskResponseRestDto>
    >(TASK_API_PATH, {
      params: {
        pageNum: pageProp.pageNum,
        pageSize: pageProp.pageSize,
      },
    });

    return taskPageEitherResponse.fold(
      (error) => Either.left(error),
      (response) => {
        return Either.right(
          mapPage<TaskResponseRestDto, TaskEntity>(
            response,
            taskResponseRestDtoToEntity
          )
        );
      }
    );
  },

  createTask: async (
    task: TaskEntity
  ): Promise<Either<AppError, TaskEntity>> => {
    const taskEitherResponse = await httpService.postRequest<
      TaskPostRequestRestDto,
      TaskResponseRestDto
    >(TASK_API_PATH, taskEntityToTaskPostRequestRestDto(task));
    return taskEitherResponse.fold(
      (error) => Either.left(error),
      (response) => Either.right(taskResponseRestDtoToEntity(response))
    );
  },

  updateTask: async (
    taskUpdateProp: TaskUpdateProps
  ): Promise<Either<AppError, void>> => {
    const taskEitherResponse = await httpService.patchRequest<
      TaskPatchRequestRestDto,
      void
    >(
      TASK_API_PATH + "/" + taskUpdateProp.id,
      taskUpdatePropsToPatchRequestRestDto(taskUpdateProp)
    );

    return taskEitherResponse.fold(
      (error) => Either.left(error),
      () => Either.right(undefined)
    );
  },

  deleteTask: async (id: string): Promise<Either<AppError, void>> => {
    const taskEitherResponse =
      await httpService.deleteRequest<TaskResponseRestDto>(
        TASK_API_PATH + "/" + id
      );
    return taskEitherResponse.fold(
      (error) => Either.left(error),
      () => Either.right(undefined)
    );
  },
});
