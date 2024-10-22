import { Route, Routes } from "react-router-dom";
import TaskListView from "./components/task-list-view/task-list-view";
import React from "react";
import TaskCreateView from "./components/task-create-view/task-create-view";
import { TaskPageProvider } from "./states/providers/task-page-provider";
import TaskView from "./components/task-view/task-view";
import TaskEditView from "./components/task-edit-view/task-edit-view";

export const ROOT_ROUTE_PATH = "/";
export const TASK_ROUTE_PATH = "/view";
export const TASK_CREATE_ROUTE_PATH = "/create";
export const TASK_EDIT_ROUTE_PATH = "/edit";
const TASK_ID_SUBPATH = "/:id";

export const TaskRoutes: React.FC = () => {
  return (
    <TaskPageProvider>
      <Routes>
        <Route path={ROOT_ROUTE_PATH} element={<TaskListView />} />
        <Route
          path={TASK_ROUTE_PATH + TASK_ID_SUBPATH}
          element={<TaskView />}
        />
        <Route path={TASK_CREATE_ROUTE_PATH} element={<TaskCreateView />} />
        <Route
          path={TASK_EDIT_ROUTE_PATH + TASK_ID_SUBPATH}
          element={<TaskEditView />}
        />
      </Routes>
    </TaskPageProvider>
  );
};
