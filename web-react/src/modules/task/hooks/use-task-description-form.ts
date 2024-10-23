import { useState } from "react";
import { useTranslation } from "react-i18next";
import { TASK_DESCRIPTION_MAX_CHARACTERS } from "../data/constants/task-limit-constants";

export const useTaskDescriptionForm = (): [
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
      newDescription.length > TASK_DESCRIPTION_MAX_CHARACTERS
    ) {
      setDescriptionError(t("task.descriptionMaxError"));
      return;
    }
    setDescriptionError("");
  };

  const isDescriptionValid: () => boolean = () => {
    if (description && description.length > TASK_DESCRIPTION_MAX_CHARACTERS) {
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
