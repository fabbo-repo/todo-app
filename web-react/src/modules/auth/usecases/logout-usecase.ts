import { signOut } from "firebase/auth";
import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { firebaseAuth } from "../../../common/config/firebase";

export const logout = async (): Promise<Either<AppError, void>> => {
  try {
    await signOut(firebaseAuth);
    return Either.right(undefined);
  } catch {
    return Either.left(new AppError("Logout error"));
  }
};
