import { initializeApp } from "firebase/app";
import {
  browserLocalPersistence,
  getAuth,
  setPersistence,
} from "firebase/auth";

const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY!,
  authDomain: `${import.meta.env.VITE_FIREBASE_PROJECT_ID!}.firebaseapp.com`,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID!,
  storageBucket: `${import.meta.env.VITE_FIREBASE_PROJECT_ID!}.appspot.com`,
  messagingSenderId: import.meta.env.VITE_FIREBASE_SENDER_ID!,
  appId: import.meta.env.VITE_FIREBASE_APP_ID!,
};

export const firebaseApp = initializeApp(firebaseConfig);

export const firebaseAuth = getAuth(firebaseApp);
setPersistence(firebaseAuth, browserLocalPersistence);
