import { createContext } from "react";
import { AccountEntity } from "../../data/entities/account-entity";

export interface AccountContextProps {
  account: AccountEntity | null;
  setAccount: React.Dispatch<React.SetStateAction<AccountEntity | null>>;
}

export const AccountContext = createContext<AccountContextProps>({
  account: null,
  setAccount: () => null,
});
