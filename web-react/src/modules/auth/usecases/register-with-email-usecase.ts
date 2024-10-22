import { createUserWithEmailAndPassword } from "firebase/auth";
import { Either } from "../../../common/data/either";
import { firebaseAuth } from "../../../common/config/firebase";
import { FirebaseError } from "firebase/app";
import { RegisterError } from "../data/errors/register-error";

export const registerWithEmail = async (
  email: string,
  password: string
): Promise<Either<RegisterError, void>> => {
  try {
    await createUserWithEmailAndPassword(firebaseAuth, email, password);
    return Either.right(undefined);
  } catch (error) {
    const firebaseError = error as FirebaseError;
    return Either.left(RegisterError.fromFirebaseCode(firebaseError.code));
  }
};
