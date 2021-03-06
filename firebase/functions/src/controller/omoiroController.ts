import { Request, Response } from 'firebase-functions';
import { db } from '../db';
import { objectToArray } from '../objectToArray';

export class OmoiroController {
    omoiros(req: Request, res: Response) {
        db.ref("/omoiros").on("value", async (snapshot) => {
            if (snapshot) res.json(await this.omoirosConvert(snapshot.val()))
        })
    }

    omoiro(req: Request, res: Response) {
        db.ref(`/omoiros/${req.params.id}`).on("value", async (snapshot) => {
            if (snapshot) res.json(
                (await this.omoirosConvert([snapshot.val()]))[0]
            )
        })
    }

    create(req: Request, res: Response) {
        if (!(
            req.body.user_id && req.body.text &&
            req.body.image_urls && req.body.color &&
            req.body.color.code && req.body.color.name && req.body.color.furigana &&
            req.body.date
        )) {
            res.json({ error: "validate error" })
            return;
        }
        const key = db.ref("/omoiros").push({
            user_id: req.body.user_id,
            date: req.body.date,
            text: req.body.text,
            image_urls: req.body.image_urls,
            color: {
                code: req.body.color.code,
                name: req.body.color.name,
                furigana: req.body.color.furigana
            }
        }).key
        res.json({ id: key })
    }

    private async omoirosConvert(omoiros: any): Promise<any[]> {
        const emo_pushs = objectToArray((await db.ref(`/emo_pushs`).once("value")).val());
        const newOmoiros = objectToArray(omoiros).map(omoiro => {
            omoiro.emo_counter = emo_pushs.filter(y => y.omoiro_id == omoiro.id).length
            return omoiro;
        })
        return newOmoiros
    }

}