import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./task-edit-view.css";
import { useTranslation } from "react-i18next";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import AppTextCheckbox from "../../../../common/components/app-text-checkbox/app-text-checkbox";
import { TaskPageContext } from "../../states/contexts/task-page-context";
import { getTask } from "../../usecases/get-task-usecase";
import { useTaskForm } from "../../hooks/use-task-form";
import { TASK_ROUTE_PATH } from "../../routes";
import AppBackButton from "../../../../common/components/app-back-button/app-back-button";
import AppTextArea from "../../../../common/components/app-text-area/app-text-area";
import {
  TASK_DESCRIPTION_MAX_CHARACTERS,
  TASK_TITLE_MAX_CHARACTERS,
} from "../../data/constants/task-limit-constants";
import AppTextField from "../../../../common/components/app-text-field/app-text-field";
import AppDateField from "../../../../common/components/app-date-field/app-date-field";
import { updateTask } from "../../usecases/update-task-usecase";
import AppErrorView from "../../../../common/components/app-error-view/app-error-view";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";

const TaskEditView: React.FC = () => {
  const { t } = useTranslation();

  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

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
  const [isCompleted, setIsCompleted] = useState(false);

  const [formError, setFormError] = useState("");

  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [loadError, setLoadError] = useState<boolean>(false);

  const { taskPage, setTaskPage } = useContext(TaskPageContext);

  useEffect(() => {
    if (!id) return;

    getTask(id).then((result) => {
      result.fold(
        () => {
          setLoadError(true);
          setIsLoading(false);
        },
        (newTask) => {
          setIsLoading(false);

          handleTitleChange(newTask.title);
          handleDescriptionChange(newTask.description);
          handleDeadlineChange(newTask.deadline);
          setIsCompleted(newTask.isCompleted);
        }
      );
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;

    if (!isFormValid()) {
      return;
    }

    setIsLoading(true);

    updateTask({
      id: id,
      title: title,
      description: description,
      isCompleted: isCompleted,
      deadline: deadline,
    }).then((result) => {
      result.fold(
        () => {
          setFormError(t("task.editView.genericError"));
          setIsLoading(false);
        },
        () => {
          setIsLoading(false);

          // Reset task page state
          setTaskPage({
            pageNumber: taskPage.pageNumber,
          });

          navigate(`${TASK_ROUTE_PATH}/${id}`);
        }
      );
    });
  };

  if (loadError) {
    return <AppErrorView title={t("task.taskLoadError")} />;
  }

  if (isLoading) {
    return <AppLoaderView />;
  }

  return (
    <div className="content">
      <div className="task-edit-view">
        <AppBackButton onClick={() => navigate(`${TASK_ROUTE_PATH}/${id}`)} />
        <div className="task-edit-view-header"/>
        <form
          onSubmit={handleSubmit}
          className="task-edit-view-form"
          noValidate
        >
          <div className="task-edit-view-form-group">
            <label htmlFor="title">{t("task.titleTitle")}</label>
            <AppTextField
              id="title"
              text={title}
              onTextChange={handleTitleChange}
              maxLength={TASK_TITLE_MAX_CHARACTERS}
              errorText={titleError}
            />
          </div>
          <div className="task-edit-view-form-group">
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
          <div className="task-edit-view-form-group">
            <label htmlFor="deadline">{t("task.deadlineTitle")}</label>
            <AppDateField
              id="deadline"
              date={deadline}
              isDateTime={true}
              onDateChange={handleDeadlineChange}
              errorText={deadlineError}
            />
          </div>
          <div className="task-edit-view-form-group">
            <AppTextCheckbox
              id="completed"
              text={t("task.editView.completedContent")}
              isChecked={isCompleted}
              onChange={(value: boolean) => setIsCompleted(value)}
            />
          </div>
          {formError && <p className="error">{formError}</p>}
          <AppTextButton text={t("task.editView.editBtn")} isSubmit={true} />
        </form>
      </div>
    </div>
  );
};

export default TaskEditView;
