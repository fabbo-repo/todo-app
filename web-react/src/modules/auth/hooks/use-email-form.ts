import { useState } from "react";
import { useTranslation } from "react-i18next";
import { isEmail } from "../../../common/utils/form-utils";

export const useEmailForm = (): [
  string,
  string,
  (newEmail: string) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [email, setEmail] = useState<string>("");
  const [emailError, setEmailError] = useState<string>("");

  const handleEmailChange: (newEmail: string) => void = (newEmail) => {
    setEmail(newEmail);

    if (!newEmail.trim()) {
      setEmailError(t("auth.emailRequiredError"));
      return;
    } else if (!isEmail(newEmail)) {
      setEmailError(t("auth.emailInvalidError"));
      return;
    }
    setEmailError("");
  };

  const isEmailValid: () => boolean = () => {
    if (!email.trim()) {
      return false;
    } else if (!isEmail(email)) {
      return false;
    }
    return true;
  };

  return [email, emailError, handleEmailChange, isEmailValid];
};
