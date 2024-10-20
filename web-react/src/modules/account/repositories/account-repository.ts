import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import HttpService from "../../../common/services/http/http-service";
import {
  ACCOUNT_API_PATH,
  ACCOUNT_IMAGE_API_SUBPATH,
} from "../data/constants/account-api-path-constants";
import { AccountPatchRequestRestDto } from "../data/dtos/account-patch-request-rest-dto";
import { AccountResponseRestDto } from "../data/dtos/account-response-rest-dto";
import { AccountEntity } from "../data/entities/account-entity";
import {
  accountResponseRestDtoToEntity,
  accountUpdatePropsToPatchRequestRestDto,
} from "../data/mappers/account-rest-mappers";
import { AccountUpdateProps } from "../data/props/account-update-props";

export interface AccountRepository {
  getAccount: () => Promise<Either<AppError, AccountEntity>>;

  updateAccount: (
    accountUpdate: AccountUpdateProps
  ) => Promise<Either<AppError, void>>;
}

export const accountRepository = (
  httpService: HttpService
): AccountRepository => ({
  getAccount: async (): Promise<Either<AppError, AccountEntity>> => {
    const accountEitherResponse =
      await httpService.getRequest<AccountResponseRestDto>(ACCOUNT_API_PATH);
    return accountEitherResponse.fold(
      (error) => Either.left(error),
      (accountResponse) =>
        Either.right(accountResponseRestDtoToEntity(accountResponse))
    );
  },

  updateAccount: async (
    accountUpdate: AccountUpdateProps
  ): Promise<Either<AppError, void>> => {
    const accountEitherResponse = await httpService.patchRequest<
      AccountPatchRequestRestDto,
      void
    >(ACCOUNT_API_PATH, accountUpdatePropsToPatchRequestRestDto(accountUpdate));

    if (accountUpdate.image) {
      const imageFormData = new FormData();
      imageFormData.append("image", accountUpdate.image);

      await httpService.postRequest<FormData, void>(
        ACCOUNT_API_PATH + ACCOUNT_IMAGE_API_SUBPATH,
        imageFormData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
    }

    return accountEitherResponse.fold(
      (error) => Either.left(error),
      () => Either.right(undefined)
    );
  },
});
