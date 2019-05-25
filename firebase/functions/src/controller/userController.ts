import { Request, Response } from 'firebase-functions';
import { db } from '../db';

export class UserController {
    users(req: Request, res: Response) {
        db.ref("/users").on("value", (snapshot) => {
            if (snapshot) res.json(snapshot.val())
        })
    }
}