import React, { useEffect, useState } from "react";
import { AccountContext } from "../contexts/account-context";
import { AccountEntity } from "../../data/entities/account-entity";
import { AppError } from "../../../../common/data/errors/app-error";
import { getAccount } from "../../usecases/get-account-usecase";
import { Either } from "../../../../common/data/either";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";
import AppErrorView from "../../../../common/components/app-error-view/app-error-view";
import { useTranslation } from "react-i18next";

interface AccountProviderProps {
  children: React.ReactNode;
}

export const AccountProvider = ({ children }: AccountProviderProps) => {
  const { i18n, t } = useTranslation();

  const [account, setAccount] = useState<AccountEntity | null>(null);
  const [isError, setIsError] = useState<boolean>(false);

  useEffect(() => {
    getAccount().then((accountEither) =>
      accountEither.fold(
        (error: AppError) => {
          setIsError(true);
          return Either.left(error);
        },
        (account) => {
          setAccount(account);

          if (account.locale) {
            i18n.changeLanguage(account.locale);
          }

          return Either.right(account);
        }
      )
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (isError) {
    return (
      <AppErrorView
        title={t("account.loadingErrorTitle")}
        message={t("account.loadingErrorMessage")}
        showHomeBtn={true}
      />
    );
  }
  if (account == null) {
    return <AppLoaderView />;
  }
  return (
    <AccountContext.Provider value={{ account, setAccount }}>
      {children}
    </AccountContext.Provider>
  );
};
