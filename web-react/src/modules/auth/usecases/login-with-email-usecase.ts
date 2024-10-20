import { signInWithEmailAndPassword } from "firebase/auth";
import { Either } from "../../../common/data/either";
import { firebaseAuth } from "../../../common/config/firebase";
import { FirebaseError } from "firebase/app";
import { LoginError } from "../data/errors/login-error";

export const loginWithEmail = async (
  email: string,
  password: string
): Promise<Either<LoginError, void>> => {
  try {
    await signInWithEmailAndPassword(firebaseAuth, email, password);
    return Either.right(undefined);
  } catch (error) {
    const firebaseError = error as FirebaseError;
    if (firebaseError.code === "auth/invalid-credential") {
      return Either.left(LoginError.credentialsError());
    }
    return Either.left(LoginError.unknownError());
  }
};
