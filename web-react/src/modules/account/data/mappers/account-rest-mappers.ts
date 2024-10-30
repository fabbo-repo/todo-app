import { AccountPatchRequestRestDto } from "../dtos/account-patch-request-rest-dto";
import { AccountResponseRestDto } from "../dtos/account-response-rest-dto";
import { AccountEntity } from "../entities/account-entity";
import { AccountUpdateProps } from "../props/account-update-props";
import accountDefaultImage from "../../../../assets/account.webp";

export function accountResponseRestDtoToEntity(
  accountResponse: AccountResponseRestDto
): AccountEntity {
  return {
    id: accountResponse.id,
    username: accountResponse.username,
    description: accountResponse.description,
    locale: accountResponse.locale,
    imageUrl: accountResponse.imageUrl ?? accountDefaultImage,
  };
}

export function accountUpdatePropsToPatchRequestRestDto(
  accountUpdateProps: AccountUpdateProps
): AccountPatchRequestRestDto {
  return {
    username: accountUpdateProps.username,
    description: accountUpdateProps.description,
    locale: accountUpdateProps.locale,
  };
}
