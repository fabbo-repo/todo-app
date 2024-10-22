import { PageEntity } from "../../../../common/data/entities/page-entity";
import { mapPage } from "../../../../common/data/mappers/page-mapper";
import { TaskPatchRequestRestDto } from "../dtos/task-patch-request-rest.dto";
import { TaskPostRequestRestDto } from "../dtos/task-post-request-rest.dto";
import { TaskResponseRestDto } from "../dtos/task-response-rest.dto";
import { TaskEntity } from "../entities/task.entity";
import { TaskUpdateProps } from "../props/task-update.props";

export function taskResponseRestDtoToEntity(
  taskResponse: TaskResponseRestDto
): TaskEntity {
  return {
    id: taskResponse.id,
    title: taskResponse.title,
    description: taskResponse.description,
    isCompleted: taskResponse.isFinished,
    deadline: new Date(taskResponse.deadline),
    createdAt: new Date(taskResponse.createdAt),
  };
}

export function taskEntityToTaskPostRequestRestDto(
  task: TaskEntity
): TaskPostRequestRestDto {
  return {
    title: task.title,
    description: task.description,
    deadline: task.deadline.toUTCString(),
  };
}

export function taskResponseRestDtoPageToEntityPage(
  taskResponsePage: PageEntity<TaskResponseRestDto>
): PageEntity<TaskEntity> {
  return mapPage<TaskResponseRestDto, TaskEntity>(
    taskResponsePage,
    taskResponseRestDtoToEntity
  );
}

export function taskUpdatePropsToPatchRequestRestDto(
  taskUpdateProps: TaskUpdateProps
): TaskPatchRequestRestDto {
  return {
    title: taskUpdateProps.title,
    description: taskUpdateProps.description,
    isFinished: taskUpdateProps.isCompleted,
    deadline: taskUpdateProps.deadline,
  };
}
