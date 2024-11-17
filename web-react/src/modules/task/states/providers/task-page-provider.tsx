import React, { useState } from "react";
import { TaskPageContext } from "../contexts/task-page-context";
import { TaskEntity } from "../../data/entities/task.entity";
import { PageEntity } from "../../../../common/data/entities/page-entity";

interface TaskPageProviderProps {
  children: React.ReactNode;
}

export interface TaskPageState {
  pageNumber: number;
  // Previous page state used to keep page data while fetching new page
  prevPage?: PageEntity<TaskEntity>;
  page?: PageEntity<TaskEntity>;
}

export const TaskPageProvider = ({ children }: TaskPageProviderProps) => {
  const [taskPage, setTaskPage] = useState<TaskPageState>({
    pageNumber: 0,
  });

  return (
    <TaskPageContext.Provider value={{ taskPage, setTaskPage }}>
      {children}
    </TaskPageContext.Provider>
  );
};
