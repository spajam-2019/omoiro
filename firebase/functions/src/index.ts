import * as functions from 'firebase-functions';

import * as Express from "express";
import indexRouter from "./routes/indexRouter";
import omoiroRouter from "./routes/omoiroRouter";
import userRouter from "./routes/userRouter";
import emoPushRouter from "./routes/emoPushRouter";

const app = Express();

app.use(Express.json());
app.use(Express.urlencoded({ extended: false }));
app.use('/', indexRouter);
app.use('/omoiros', omoiroRouter);
app.use('/users', userRouter);
app.use('/emo_pushs', emoPushRouter);

exports.app = functions.https.onRequest(app);