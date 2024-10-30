import { useState } from "react";
import { useTranslation } from "react-i18next";

export const useRegisterConfirmPasswordForm = (): [
  string,
  string,
  (password: string, newConfirmPassword: string) => void,
  (password: string) => boolean
] => {
  const { t } = useTranslation();

  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [confirmPasswordError, setConfirmPasswordError] = useState<string>("");

  const handleConfirmPasswordChange: (
    password: string,
    newConfirmPassword: string
  ) => void = (password: string, newConfirmPassword: string) => {
    setConfirmPassword(newConfirmPassword);

    if (newConfirmPassword !== password) {
      setConfirmPasswordError(t("auth.registerView.passwordMatchError"));
      return;
    }
    setConfirmPasswordError("");
  };

  const isConfirmPasswordValid: (password: string) => boolean = (
    password: string
  ) => {
    if (confirmPassword !== password) {
      return false;
    }
    return true;
  };

  return [
    confirmPassword,
    confirmPasswordError,
    handleConfirmPasswordChange,
    isConfirmPasswordValid,
  ];
};
