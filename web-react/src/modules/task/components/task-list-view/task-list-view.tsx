import React, { useContext, useEffect } from "react";
import { Plus } from "lucide-react";
import { Link } from "react-router-dom";
import { TASK_CREATE_ROUTE_PATH } from "../../routes";
import { useTranslation } from "react-i18next";
import PaginationBox from "../../../../common/components/pagination-box/pagination-box";
import "./task-list-view.css";
import { TaskPageContext } from "../../states/contexts/task-page-context";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";
import { getTaskPage } from "../../usecases/get-task-page-usecase";
import TaskCard from "../task-card/task-card";

const TaskListView: React.FC = () => {
  const { t } = useTranslation();

  const { taskPage, setTaskPage } = useContext(TaskPageContext);

  useEffect(() => {
    // Use effect will be executed only if task page content
    // is undefined
    if (taskPage.page) return;

    getTaskPage(taskPage.pageNumber).then((result) => {
      result.fold(
        () => {},
        (newPage) => {
          setTaskPage({
            pageNumber: taskPage.pageNumber,
            page: newPage,
          });
        }
      );
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [taskPage.pageNumber, taskPage.page]);

  const onNewTaskPage = (pageNumber: number) => {
    setTaskPage({
      pageNumber: pageNumber,
    });
  };

  if (!taskPage.page && !taskPage.prevPage) {
    return <AppLoaderView />;
  }

  const taskPageData = taskPage.page ?? taskPage.prevPage;

  return (
    <div className="content">
      <div className="task-list-view">
        <Link to={TASK_CREATE_ROUTE_PATH} className="add-task-button">
          <Plus size={24} />
        </Link>
        {taskPageData!.results.length === 0 ? (
          <p className="no-tasks">{t("task.listView.noTasks")}</p>
        ) : (
          <>
            {taskPageData!.results.map((task) => (
              <TaskCard key={task.id} task={task} />
            ))}
            <PaginationBox
              itemsPerPage={10}
              totalItems={taskPageData!.totalElements}
              onNewPage={onNewTaskPage}
              currentPage={taskPageData!.pageIndex}
            />
          </>
        )}
      </div>
    </div>
  );
};

export default TaskListView;
