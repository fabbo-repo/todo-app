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
    const credentials = await signInWithEmailAndPassword(
      firebaseAuth,
      email,
      password
    );

    if (!credentials.user.emailVerified) {
      return Either.left(LoginError.emailNotVerifiedError());
    }

    return Either.right(undefined);
  } catch (error) {
    const firebaseError = error as FirebaseError;
    return Either.left(LoginError.fromFirebaseCode(firebaseError.code));
  }
};
