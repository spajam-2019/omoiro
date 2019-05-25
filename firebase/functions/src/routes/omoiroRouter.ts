import { Router } from "express";


const router = Router();

router.get('/show', (req, res) => {
    res.json([
        {
            "id": 0,
            "user_id": 0,
            "order": 0,
            "text": "猫かわいい",
            "image_urls": [
                "https://i.ytimg.com/vi/XeJ2kqfAq4Q/maxresdefault.jpg",
            ],
            "color": {
                "code": "#FFFFFF",
                "name": "猫愛",
                "furigana": "じつはいぬだいすき"
            },
            "emmo_push_count": 1
        },
        {
            "id": 1,
            "user_id": 1,
            "order": 1,
            "text": "D言語はコンパイル時計算が得意です",
            "image_urls": [
                "https://camo.qiitausercontent.com/2dab24acfc44cc8294ee7f2324740880bc7c5539/68747470733a2f2f71696974612d696d6167652d73746f72652e73332e616d617a6f6e6177732e636f6d2f302f31313731342f34636365353633632d633365642d653365612d333664612d3234306266653331333135382e706e67"
            ],
            "color": {
                "code": "#FFFF00",
                "name": "暗黒計算魔術",
                "furigana": "こんぱいるじけいさん"
            },
            "emmo_push_count": 0
        }
    ]);
});

export default router;