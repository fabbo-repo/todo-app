import { useState } from "react";
import { useTranslation } from "react-i18next";

export const useTaskDeadlineForm = (): [
  Date | undefined,
  string,
  (newDeadline: Date | undefined) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [deadline, setDeadline] = useState<Date | undefined>();
  const [deadlineError, setDeadlineError] = useState<string>("");

  const handleDeadlineChange: (newDeadline: Date | undefined) => void = (
    newDeadline
  ) => {
    setDeadline(newDeadline);

    if (newDeadline && new Date(newDeadline) <= new Date()) {
      setDeadlineError(t("task.descriptionMaxError"));
      return;
    }
    setDeadlineError("");
  };

  const isDeadlineValid: () => boolean = () => {
    if (deadline && new Date(deadline) <= new Date()) {
      return false;
    }
    return true;
  };

  return [deadline, deadlineError, handleDeadlineChange, isDeadlineValid];
};
