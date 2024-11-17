import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./task-create-view.css";
import { useTranslation } from "react-i18next";
import { ROOT_ROUTE_PATH } from "../../routes";
import AppBackButton from "../../../../common/components/app-back-button/app-back-button";
import { TaskPageContext } from "../../states/contexts/task-page-context";
import { createTask } from "../../usecases/create-task-usecase";
import { useTaskForm } from "../../hooks/use-task-form";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import AppDateField from "../../../../common/components/app-date-field/app-date-field";
import AppTextArea from "../../../../common/components/app-text-area/app-text-area";
import {
  TASK_DESCRIPTION_MAX_CHARACTERS,
  TASK_TITLE_MAX_CHARACTERS,
} from "../../data/constants/task-limit-constants";
import AppTextField from "../../../../common/components/app-text-field/app-text-field";

const TaskCreateView: React.FC = () => {
  const { t } = useTranslation();

  const navigate = useNavigate();

  const { taskPage, setTaskPage } = useContext(TaskPageContext);

  const [isLoading, setIsLoading] = useState<boolean>(false);

  // Form hooks
  const [
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
  ] = useTaskForm();

  const [formError, setFormError] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!isFormValid()) {
      return;
    }

    setIsLoading(true);

    createTask({
      title: title,
      description: description,
      isCompleted: false,
      deadline: deadline,
    }).then((result) => {
      result.fold(
        () => {
          setFormError(t("task.createView.genericError"));
          setIsLoading(false);
        },
        () => {
          setIsLoading(false);

          // Reset task page state
          setTaskPage({
            pageNumber: taskPage.pageNumber,
          });

          navigate(ROOT_ROUTE_PATH);
        }
      );
    });
  };

  if (isLoading) {
    return <AppLoaderView />;
  }

  return (
    <div className="content">
      <form onSubmit={handleSubmit} className="task-create-view" noValidate>
        <AppBackButton onClick={() => navigate(ROOT_ROUTE_PATH)} />
        <div className="task-create-view-form-group">
          <h2></h2>
          <label htmlFor="title">{t("task.titleTitle")}</label>
          <AppTextField
            id="title"
            text={title}
            onTextChange={handleTitleChange}
            maxLength={TASK_TITLE_MAX_CHARACTERS}
            errorText={titleError}
          />
        </div>
        <div className="task-create-view-form-group">
          <label htmlFor="description">{t("task.descriptionTitle")}</label>
          <AppTextArea
            id="description"
            text={description}
            onTextChange={handleDescriptionChange}
            errorText={descriptionError}
            rows={3}
            maxLength={TASK_DESCRIPTION_MAX_CHARACTERS}
          />
        </div>
        <div className="task-create-view-form-group">
          <label htmlFor="deadline">{t("task.deadlineTitle")}</label>
          <AppDateField
            id="deadline"
            date={deadline}
            isDateTime={true}
            onDateChange={handleDeadlineChange}
            errorText={deadlineError}
          />
        </div>
        {formError && <p className="error">{formError}</p>}
        <AppTextButton text={t("task.createView.createBtn")} isSubmit={true} />
      </form>
    </div>
  );
};

export default TaskCreateView;
