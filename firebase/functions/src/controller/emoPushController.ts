import { Request, Response } from 'firebase-functions';
import { db } from '../db';
import { objectToArray } from '../objectToArray';

export class EmoController {
    async insert(req: Request, res: Response) {
        if (!(req.body.user_id && req.body.omoiro_id)) {
            res.sendStatus(400);
            return;
        }
        if (await this.deleteEmoPushs(req.body.user_id, req.body.omoiro_id)) {
            res.json({ ok: "ok delete" })
            return;
        }
        db.ref("/emo_pushs").push({ "user_id": req.body.user_id, "omoiro_id": req.body.omoiro_id });
        res.json({ ok: "ok" })
    }

    //emo_pushsを返す
    emoPushs(req: Request, res: Response) {
        db.ref("/emo_pushs").on("value", (snapshot) => {
            if (snapshot) res.json(objectToArray(snapshot.val()));
        });
    }

    private async deleteEmoPushs(user_id: number, omoiro_id: number) {

        const emo_push = objectToArray((await db.ref("/emo_pushs").once("value")).val())
            .find(x => x.omoiro_id == omoiro_id && x.user_id == user_id);
        if (emo_push) {
            db.ref(`/emo_pushs/${emo_push.id}`).set(null);
            return true;
        }
        return false;
    }
}