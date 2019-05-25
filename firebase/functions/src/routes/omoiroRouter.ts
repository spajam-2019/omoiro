import { Router } from "express";
import { OmoiroController } from "../controller/omoiroController";

const omoiroController = new OmoiroController();
const router = Router();

router.get('/', (req, res) => {
    omoiroController.omoiros(req, res);
});

router.get('/:id', (req, res) => {
    omoiroController.omoiro(req, res);
});

router.post('/create', (req, res) => {
    omoiroController.create(req, res);
});

export default router;