import { Router } from "express";


const router = Router();
/* GET home page. */
router.get('/', (req, res) => {
    res.json("Hello World");
});

export default router;