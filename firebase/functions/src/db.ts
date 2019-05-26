import * as Admin from "firebase-admin";
import { authData } from "./auth";

const admin = Admin.initializeApp({
    credential: Admin.credential.cert(authData),
    databaseURL: "https://omoiro.firebaseio.com" //データベースのURL
});

export const db = admin.database();
