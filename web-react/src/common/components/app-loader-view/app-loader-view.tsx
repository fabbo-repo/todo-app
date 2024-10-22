import React from "react";
import "./app-loader-view.css";

interface AppLoaderViewProps {
  message?: string;
}

const AppLoaderView: React.FC<AppLoaderViewProps> = ({ message }) => {
  return (
    <div className="app-loader-view-container">
      <div className="app-loader-view"></div>
      <p className="app-loader-view-text">{message}</p>
    </div>
  );
};

export default AppLoaderView;
