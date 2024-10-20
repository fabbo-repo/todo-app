import { useState } from "react";
import { useTranslation } from "react-i18next";

export const useLoginPasswordForm = (): [
  string,
  string,
  (newPassword: string) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [password, setPassword] = useState<string>("");
  const [passwordError, setPasswordError] = useState<string>("");

  const handlePasswordChange: (newPassword: string) => void = (newPassword) => {
    setPassword(newPassword);

    if (!newPassword.trim()) {
      setPasswordError(t("auth.passwordRequiredError"));
      return;
    }
    setPasswordError("");
  };

  const isPasswordValid: () => boolean = () => {
    if (!password.trim()) {
      return false;
    }
    return true;
  };

  return [password, passwordError, handlePasswordChange, isPasswordValid];
};
