import { Router } from "express";
import { EmoController } from "../controller/emoPushController";

const emoController = new EmoController();
const router = Router();

router.post('/insert', (req, res) => {
    emoController.insert(req, res);
});

router.get('/', (req, res) => {
    emoController.emoPushs(req, res);
});


export default router;