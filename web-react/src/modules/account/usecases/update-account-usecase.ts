import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { accountRepositoryInstance } from "../repositories/repository-instances";
import { AccountUpdateProps } from "../data/props/account-update-props";
import { AccountEntity } from "../data/entities/account-entity";
import { getAccount } from "./get-account-usecase";

export const updateAccount = async (
  accountUpdateProps: AccountUpdateProps
): Promise<Either<AppError, AccountEntity>> => {
  await accountRepositoryInstance.updateAccount(accountUpdateProps);

  return await getAccount();
};
