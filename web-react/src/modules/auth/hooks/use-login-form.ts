import { useLoginPasswordForm } from "./use-login-password-form";
import { useEmailForm } from "./use-email-form";

export const useLoginForm = (): [
  string,
  string,
  string,
  string,
  (newEmail: string) => void,
  (newPassword: string) => void,
  () => boolean
] => {
  const [email, emailError, handleEmailChange, isEmailValid] = useEmailForm();
  const [password, passwordError, handlePasswordChange, isPasswordValid] =
    useLoginPasswordForm();

  const isFormValid: () => boolean = () => {
    // Check if email is valid
    if (!isEmailValid() && !emailError) {
      handleEmailChange(email);
    }
    // Check if password is valid
    if (!isPasswordValid() && !passwordError) {
      handlePasswordChange(password);
    }
    if (!isEmailValid() || !isPasswordValid()) {
      return false;
    }
    return true;
  };

  return [
    email,
    password,
    emailError,
    passwordError,
    handleEmailChange,
    handlePasswordChange,
    isFormValid,
  ];
};
