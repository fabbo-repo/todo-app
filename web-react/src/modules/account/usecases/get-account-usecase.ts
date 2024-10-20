import { AccountEntity } from '../data/entities/account-entity';
import { Either } from '../../../common/data/either';
import { AppError } from '../../../common/data/errors/app-error';
import { accountRepositoryInstance } from '../repositories/repository-instances';

export const getAccount = async (): Promise<Either<AppError, AccountEntity>> => {
    return await accountRepositoryInstance.getAccount();
};
