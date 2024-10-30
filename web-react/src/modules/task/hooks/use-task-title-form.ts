import { useState } from "react";
import { useTranslation } from "react-i18next";
import { TASK_TITLE_MAX_CHARACTERS } from "../data/constants/task-limit-constants";

export const useTaskTitleForm = (): [
  string,
  string,
  (newTitle: string) => void,
  () => boolean
] => {
  const { t } = useTranslation();

  const [title, setTitle] = useState<string>("");
  const [titleError, setTitleError] = useState<string>("");

  const handleTitleChange: (newTitle: string) => void = (newTitle) => {
    setTitle(newTitle);

    if (!newTitle.trim()) {
      setTitleError(t("task.titleRequiredError"));
      return;
    } else if (newTitle.length > TASK_TITLE_MAX_CHARACTERS) {
      setTitleError(t("task.titleMaxError"));
      return;
    }
    setTitleError("");
  };

  const isTitleValid: () => boolean = () => {
    if (!title.trim() || title.length > TASK_TITLE_MAX_CHARACTERS) {
      return false;
    }
    return true;
  };

  return [title, titleError, handleTitleChange, isTitleValid];
};
