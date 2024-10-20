import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./register-view.css";
import AppTextInput from "../../../../common/components/app-text-field/app-text-field";
import { LOGIN_ROUTE_PATH } from "../../routes";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import { useTranslation } from "react-i18next";
import { registerWithEmail } from "../../usecases/register-with-email-usecase";
import { useEmailForm } from "../../hooks/use-email-form";
import { useRegisterPasswordForm } from "../../hooks/use-register-password-form";
import { useRegisterConfirmPasswordForm } from "../../hooks/use-register-confirm-password-form";
import {
  RegisterError,
  RegisterErrorTypeEnum,
} from "../../data/errors/register-error";

const RegisterView: React.FC = () => {
  const { t } = useTranslation();

  const navigate = useNavigate();

  // Form hooks
  const [email, emailError, handleEmailChange, isEmailValid] = useEmailForm();
  const [password, passwordError, handlePasswordChange, isPasswordValid] =
    useRegisterPasswordForm();
  const [
    confirmPassword,
    confirmPasswordError,
    handleConfirmPasswordChange,
    isConfirmPasswordValid,
  ] = useRegisterConfirmPasswordForm();

  const [formError, setFormError] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    // Check if email is valid
    if (!isEmailValid() && !emailError) {
      handleEmailChange(email);
    }
    // Check if password is valid
    if (!isPasswordValid() && !passwordError) {
      handlePasswordChange(password);
    }
    // Check if confirm password is valid
    if (!isConfirmPasswordValid(password) && !confirmPasswordError) {
      handleConfirmPasswordChange(password, confirmPassword);
    }
    if (
      !isEmailValid() ||
      !isPasswordValid() ||
      !isConfirmPasswordValid(password)
    ) {
      return;
    }

    // Check if email is valid
    if (!isEmailValid() && !emailError) {
      handleEmailChange(email);
    }

    registerWithEmail(email, password).then((result) => {
      result.fold(
        (error: RegisterError) => {
          if (error.type === RegisterErrorTypeEnum.WEAK_PASSWORD_ERROR) {
            setFormError(t("auth.registerView.weakPasswordError"));
          } else {
            setFormError(t("auth.registerView.registerError"));
          }
        },
        () => {
          navigate(LOGIN_ROUTE_PATH);
        }
      );
    });
  };

  return (
    <div className="register-view-container">
      <div className="register-view-form">
        <h2 className="register-view-title">{t("auth.registerView.title")}</h2>
        <form onSubmit={handleSubmit}>
          <div className="register-view-form-group">
            <label htmlFor="email">{t("auth.emailLabel")}</label>
            <AppTextInput
              id="email"
              text={email}
              onTextChange={(e) => handleEmailChange(e)}
              errorText={emailError}
            />
          </div>
          <div className="register-view-form-group">
            <label htmlFor="password">{t("auth.passwordLabel")}</label>
            <AppTextInput
              id="password"
              type="password"
              text={password}
              onTextChange={(e) => handlePasswordChange(e)}
              errorText={passwordError}
            />
          </div>
          <div className="register-view-form-group">
            <label htmlFor="confirm-password">
              {t("auth.confirmPasswordLabel")}
            </label>
            <AppTextInput
              id="confirm-password"
              type="password"
              text={confirmPassword}
              onTextChange={(e) => handleConfirmPasswordChange(password, e)}
              errorText={confirmPasswordError}
            />
          </div>
          {formError && <p className="error">{formError}</p>}
          <AppTextButton
            text={t("auth.registerView.registerBtn")}
            isSubmit={true}
          />
        </form>
        <Link to={LOGIN_ROUTE_PATH} className="register-view-login-link">
          {t("auth.registerView.loginLink")}
        </Link>
      </div>
    </div>
  );
};

export default RegisterView;
