import { useEmailForm } from "./use-email-form";
import { useRegisterPasswordForm } from "./use-register-password-form";
import { useRegisterConfirmPasswordForm } from "./use-register-confirm-password-form";

export const useRegisterForm = (): [
  string,
  string,
  string,
  string,
  string,
  string,
  (newEmail: string) => void,
  (newPassword: string) => void,
  (password: string, newConfirmPassword: string) => void,
  () => boolean
] => {
  const [email, emailError, handleEmailChange, isEmailValid] = useEmailForm();
  const [password, passwordError, handlePasswordChange, isPasswordValid] =
    useRegisterPasswordForm();
  const [
    confirmPassword,
    confirmPasswordError,
    handleConfirmPasswordChange,
    isConfirmPasswordValid,
  ] = useRegisterConfirmPasswordForm();

  const isFormValid: () => boolean = () => {
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
      return false;
    }
    return true;
  };

  return [
    email,
    password,
    confirmPassword,
    emailError,
    passwordError,
    confirmPasswordError,
    handleEmailChange,
    handlePasswordChange,
    handleConfirmPasswordChange,
    isFormValid,
  ];
};
