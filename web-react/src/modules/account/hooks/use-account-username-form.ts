import { useState } from "react";
import { useTranslation } from "react-i18next";
import { ACCOUNT_USERNAME_MAX_CHARACTERS } from "../data/constants/account-limit-constants";

export const useAccountUsernameForm = (): [
  string,
  string,
  (newUsername: string) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [username, setUsername] = useState<string>("");
  const [usernameError, setUsernameError] = useState<string>("");

  const handleUsernameChange: (newUsername: string) => void = (newUsername) => {
    setUsername(newUsername);

    if (!newUsername.trim()) {
      setUsernameError(t("account.usernameRequiredError"));
      return;
    } else if (newUsername.length > ACCOUNT_USERNAME_MAX_CHARACTERS) {
      setUsernameError(t("account.usernameMaxError"));
      return;
    }
    setUsernameError("");
  };

  const isUsernameValid: () => boolean = () => {
    if (!username.trim() || username.length > ACCOUNT_USERNAME_MAX_CHARACTERS) {
      return false;
    }
    return true;
  };

  return [username, usernameError, handleUsernameChange, isUsernameValid];
};
