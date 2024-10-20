import { Route, Routes } from "react-router-dom";
import TaskListView from "./components/task-list.view/task-list-view";
import React from "react";

export const ROOT_ROUTE_PATH = "/";
export const TASK_ROUTE_PATH = "/view/:id";
export const TASK_CREATE_ROUTE_PATH = "/create";
export const TASK_EDIT_ROUTE_PATH = "/edit/:id";

export const TaskRoutes: React.FC = () => {
  return (
    <Routes>
      <Route path={ROOT_ROUTE_PATH} element={<TaskListView />} />
      <Route path={TASK_ROUTE_PATH} element={<TaskView />} />
      <Route path={TASK_CREATE_ROUTE_PATH} element={<TaskCreateView />} />
      <Route path={TASK_EDIT_ROUTE_PATH} element={<TaskEditView />} />
    </Routes>
  );
};
