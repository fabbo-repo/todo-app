import { taskApiServiceInstance } from "../../../common/services/instances";
import { taskRepository } from "./task-repository";

export const taskRepositoryInstance = taskRepository(taskApiServiceInstance);
