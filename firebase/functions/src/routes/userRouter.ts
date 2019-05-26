import { Router } from "express";
import { UserController } from "../controller/userController";


const router = Router();

const userController = new UserController();

router.get('/', (req, res) => {
    userController.users(req, res);
});

export default router;