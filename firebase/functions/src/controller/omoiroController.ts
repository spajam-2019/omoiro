import { Request, Response } from 'firebase-functions';
import { db } from '../db';

export class OmoiroController {
    omoiros(req: Request, res: Response) {
        db.ref("/omoiros").on("value", (snapshot) => {
            if (snapshot) res.json(snapshot.val())
        })
    }

    create(req: Request, res: Response) {
        if (!(req.body.user_id && req.body.text && req.body.image_urls && req.body.color)) {
            res.json({ error: "validate error" })
            return;
        }
        db.ref("/omoiros").push({
            user_id: req.body.user_id,
            date: new Date().getTime(),
            text: req.body.text,
            image_urls: req.body.image_urls,
            color: req.body.color
        })
        res.json({ ok: "ok" })
    }

}