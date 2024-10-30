import { useAccountUsernameForm } from "./use-account-username-form";
import { useAccountDescriptionForm } from "./use-account-description-form";

export const useAccountForm = (): [
  string,
  string,
  string,
  string,
  (newUsername: string) => void,
  (newDescription: string) => void,
  () => boolean
] => {
  const [username, usernameError, handleUsernameChange, isUsernameValid] =
    useAccountUsernameForm();
  const [
    description,
    descriptionError,
    handleDescriptionChange,
    isDescriptionValid,
  ] = useAccountDescriptionForm();

  const isFormValid: () => boolean = () => {
    // Check if username is valid
    if (!isUsernameValid() && !usernameError) {
      handleUsernameChange(username);
    }
    // Check if description is valid
    if (!isDescriptionValid() && !descriptionError) {
      handleDescriptionChange(description);
    }
    if (!isUsernameValid() || !isDescriptionValid()) {
      return false;
    }
    return true;
  };

  return [
    username,
    description,
    usernameError,
    descriptionError,
    handleUsernameChange,
    handleDescriptionChange,
    isFormValid,
  ];
};
