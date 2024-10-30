import { sendEmailVerification, signOut } from "firebase/auth";
import { Either } from "../../../common/data/either";
import { AppError } from "../../../common/data/errors/app-error";
import { firebaseAuth } from "../../../common/config/firebase";

export const sendEmailVerificationByEmail = async (): Promise<
  Either<AppError, void>
> => {
  try {
    if (firebaseAuth.currentUser) {
      await sendEmailVerification(firebaseAuth.currentUser);
      await signOut(firebaseAuth);
    }

    return Either.right(undefined);
  } catch (error) {
    console.log(error);
    return Either.left(new AppError(""));
  }
};
