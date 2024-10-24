import { useState } from "react";
import { useTranslation } from "react-i18next";
import { ACCOUNT_DESCRIPTION_MAX_CHARACTERS } from "../data/constants/account-limit-constants";

export const useAccountDescriptionForm = (): [
  string,
  string,
  (newDescription: string) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [description, setDescription] = useState<string>("");
  const [descriptionError, setDescriptionError] = useState<string>("");

  const handleDescriptionChange: (newDescription: string) => void = (
    newDescription
  ) => {
    setDescription(newDescription);

    if (
      newDescription &&
      newDescription.length > ACCOUNT_DESCRIPTION_MAX_CHARACTERS
    ) {
      setDescriptionError(t("account.descriptionMaxError"));
      return;
    }
    setDescriptionError("");
  };

  const isDescriptionValid: () => boolean = () => {
    if (
      description &&
      description.length > ACCOUNT_DESCRIPTION_MAX_CHARACTERS
    ) {
      return false;
    }
    return true;
  };

  return [
    description,
    descriptionError,
    handleDescriptionChange,
    isDescriptionValid,
  ];
};
