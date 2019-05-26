import { Request, Response } from 'firebase-functions';
import { db } from '../db';

export class EmoController {
    insert(req: Request, res: Response) {
        db.ref("/emo_pushs").push({ "user_id": req.body.user_id, "omoiro_id": req.body.omoiro_id });
        res.send("");
    }

    //user_idにひもづくemo_pushsを返す
    emoPushs(req: Request, res: Response) {
        db.ref("/emo_pushs").on("value", (snapshot) => {
            if (snapshot) res.json(snapshot.val());
        });
    }
}