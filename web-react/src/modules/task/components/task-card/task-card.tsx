import React, { useContext, useState } from "react";
import { CheckCircle, Circle, Eye, Trash2, Calendar } from "lucide-react";
import { Link } from "react-router-dom";
import "./task-card.css";
import { TaskEntity } from "../../data/entities/task.entity";
import AppConfirmationDialog from "../../../../common/components/app-confirmation-dialog/app-confirmation-dialog";
import { TaskPageContext } from "../../states/contexts/task-page-context";
import { deleteTask } from "../../usecases/delete-task-usecase";
import { useTranslation } from "react-i18next";
import { updateTask } from "../../usecases/update-task-usecase";
import { TASK_ROUTE_PATH } from "../../routes";

interface TaskCardProps {
  task: TaskEntity;
}

const TodoItem: React.FC<TaskCardProps> = ({ task }) => {
  const { i18n, t } = useTranslation();

  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);

  const { taskPage, setTaskPage } = useContext(TaskPageContext);

  const handleToggleComplete = () => {
    updateTask({
      id: task.id!,
      isCompleted: !task.isCompleted,
    }).then((result) => {
      result.fold(
        () => {},
        () => {
          setTaskPage({
            pageNumber: taskPage.pageNumber,
          });
        }
      );
    });
  };

  const handleDeleteClick = () => {
    setIsDeleteDialogOpen(true);
  };

  const handleConfirmDelete = () => {
    deleteTask(task.id ?? "").then((result) => {
      result.fold(
        () => {},
        () => {
          setTaskPage({
            pageNumber: taskPage.pageNumber,
          });
        }
      );
    });

    setIsDeleteDialogOpen(false);
  };

  const formatDate = (date: Date) => {
    return new Date(date).toLocaleDateString(i18n.language, {
      year: "numeric",
      month: "short",
      day: "numeric",
    });
  };

  return (
    <>
      <div className="task-card">
        <div className="task-card-header">
          <h3
            className={`task-card-title ${task.isCompleted ? "completed" : ""}`}
          >
            {task.title}
          </h3>
          <div className="task-card-actions">
            <button onClick={handleToggleComplete}>
              {task.isCompleted ? (
                <CheckCircle size={20} />
              ) : (
                <Circle size={20} />
              )}
            </button>
            <Link to={`${TASK_ROUTE_PATH}/${task.id}`}>
              <Eye size={20} />
            </Link>
            <button onClick={handleDeleteClick}>
              <Trash2 size={20} />
            </button>
          </div>
        </div>
        <p className="task-card-description">{task.description}</p>
        {task.deadline && (
          <div className="task-card-deadline">
            <Calendar size={16} />
            <span>
              {t("task.view.deadlineTitle")}: {formatDate(task.deadline)}
            </span>
          </div>
        )}
      </div>
      <AppConfirmationDialog
        isOpen={isDeleteDialogOpen}
        onClose={() => setIsDeleteDialogOpen(false)}
        title={t("task.deleteTaskDialogTitle")}
        content={t("task.deleteTaskDialogContent")}
        onConfirm={handleConfirmDelete}
      />
    </>
  );
};

export default TodoItem;
