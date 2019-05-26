import { Router } from "express";
import { OmoiroController } from "../controller/omoiroController";

const omoiroController = new OmoiroController();
const router = Router();

router.get('/', (req, res) => {
    omoiroController.omoiros(req, res);
});

router.post('/create', (req, res) => {
    omoiroController.create(req, res);
});

export default router;