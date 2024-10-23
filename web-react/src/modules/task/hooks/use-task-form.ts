import { useTaskDeadlineForm } from "./use-task-deadline-form";
import { useTaskDescriptionForm } from "./use-task-description-form";
import { useTaskTitleForm } from "./use-task-title-form";

export const useTaskForm = (): [
  string,
  string,
  Date | undefined,
  string,
  string,
  string,
  (newTitle: string) => void,
  (newDescription: string) => void,
  (newDeadline: Date | undefined) => void,
  () => boolean
] => {
  const [title, titleError, handleTitleChange, isTitleValid] =
    useTaskTitleForm();
  const [
    description,
    descriptionError,
    handleDescriptionChange,
    isDescriptionValid,
  ] = useTaskDescriptionForm();
  const [deadline, deadlineError, handleDeadlineChange, isDeadlineValid] =
    useTaskDeadlineForm();

  const isFormValid: () => boolean = () => {
    // Check if title is valid
    if (!isTitleValid() && !titleError) {
      handleTitleChange(title);
    }
    // Check if description is valid
    if (!isDescriptionValid() && !descriptionError) {
      handleDescriptionChange(description);
    }
    // Check if deadline is valid
    if (!isDeadlineValid() && !deadlineError) {
      handleDeadlineChange(deadline);
    }
    if (!isTitleValid() || !isDescriptionValid() || !isDeadlineValid()) {
      return false;
    }
    return true;
  };

  return [
    title,
    description,
    deadline,
    titleError,
    descriptionError,
    deadlineError,
    handleTitleChange,
    handleDescriptionChange,
    handleDeadlineChange,
    isFormValid,
  ];
};
