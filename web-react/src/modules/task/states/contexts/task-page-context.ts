import { createContext } from "react";
import { TaskPageState } from "../providers/task-page-provider";

export interface TaskPageIndexProps {
  taskPage: TaskPageState;
  setTaskPage: React.Dispatch<React.SetStateAction<TaskPageState>>;
}

export const TaskPageContext = createContext<TaskPageIndexProps>({
  taskPage: {
    pageNumber: 0,
  },
  setTaskPage: () => null,
});
