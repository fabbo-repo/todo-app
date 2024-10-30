import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./task-view.css";
import { TaskEntity } from "../../data/entities/task.entity";
import { getTask } from "../../usecases/get-task-usecase";
import { useTranslation } from "react-i18next";
import { ROOT_ROUTE_PATH, TASK_EDIT_ROUTE_PATH } from "../../routes";
import AppBackButton from "../../../../common/components/app-back-button/app-back-button";
import AppEditButton from "../../../../common/components/app-edit-button/app-edit-button";
import AppErrorView from "../../../../common/components/app-error-view/app-error-view";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";

const TaskView: React.FC = () => {
  const { t } = useTranslation();

  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [task, setTask] = useState<TaskEntity | null>(null);
  const [loadError, setLoadError] = useState<boolean>(false);

  useEffect(() => {
    if (!id) return;

    getTask(id).then((result) => {
      result.fold(
        () => {
          setLoadError(true);
        },
        (newTask) => {
          setTask(newTask);
        }
      );
    });
  }, [id]);

  if (loadError) {
    return <AppErrorView title={t("task.taskLoadError")} />;
  }

  if (!task) {
    return <AppLoaderView />;
  }

  return (
    <div className="content">
      <div className="task-view">
        <div className="task-view-header">
          <AppBackButton onClick={() => navigate(ROOT_ROUTE_PATH)} />
          <AppEditButton
            onClick={() => navigate(TASK_EDIT_ROUTE_PATH + "/" + task.id)}
          />
        </div>
        <div className="task-view-content">
          <h2 className="task-view-title">{task.title}</h2>
          <p className="task-view-description">{task.description}</p>
          <div className="task-view-details">
            <p>
              <span className="detail-label">{t("task.view.stateTitle")}:</span>
              <span
                className={`status-badge ${
                  task.isCompleted ? "completed" : "pending"
                }`}
              >
                {task.isCompleted
                  ? t("task.view.completedContent")
                  : t("task.view.pendingContent")}
              </span>
            </p>
            {task.createdAt && (
              <p>
                <span className="detail-label">
                  {t("task.view.createdAtTitle")}:
                </span>{" "}
                {new Date(task.createdAt).toLocaleString()}
              </p>
            )}
            {task.deadline && (
              <p>
                <span className="detail-label">
                  {t("task.view.deadlineTitle")}:
                </span>{" "}
                {new Date(task.deadline).toLocaleString()}
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TaskView;
