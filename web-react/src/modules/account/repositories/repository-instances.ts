import { taskApiServiceInstance } from "../../../common/services/instances";
import { accountRepository } from "./account-repository";

export const accountRepositoryInstance = accountRepository(
  taskApiServiceInstance
);
