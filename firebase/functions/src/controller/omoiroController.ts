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
        if (!(req.body.user_id && req.body.text && req.body.image_urls && req.body.color)) {
            res.json({ error: "validate error" })
            return;
        }
        const key = db.ref("/omoiros").push({
            user_id: req.body.user_id,
            date: new Date().getTime(),
            text: req.body.text,
            image_urls: req.body.image_urls,
            color: req.body.color
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