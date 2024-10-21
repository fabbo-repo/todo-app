import React from "react";
import "./app-confirmation-dialog.css";
import { useTranslation } from "react-i18next";

interface AppConfirmationDialogProps {
  isOpen: boolean;
  title: string;
  content: string;
  onConfirm: () => void;
  onClose: () => void;
  confirmText?: string;
  cancelText?: string;
}

const AppConfirmationDialog: React.FC<AppConfirmationDialogProps> = ({
  isOpen,
  title,
  content,
  onConfirm,
  onClose,
  confirmText,
  cancelText,
}) => {
  const { t } = useTranslation();

  if (!isOpen) return null;

  confirmText ??= t("generic.confirm");
  cancelText ??= t("generic.cancel");

  return (
    <div className="app-confirmation-dialog-overlay" onClick={onClose}>
      <div
        className="app-confirmation-dialog"
        onClick={(e) => e.stopPropagation()}
      >
        <h2 className="app-confirmation-dialog-title">{title}</h2>
        <div className="app-confirmation-dialog-content">{content}</div>
        <div className="app-confirmation-dialog-actions">
          <button
            className="app-confirmation-dialog-button app-confirmation-dialog-button-primary"
            onClick={onConfirm}
          >
            {confirmText}
          </button>
          <button
            className="app-confirmation-dialog-button app-confirmation-dialog-button-secondary"
            onClick={onClose}
          >
            {cancelText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default AppConfirmationDialog;
