import * as functions from 'firebase-functions';

import * as Express from "express";
import indexRouter from "./routes/indexRouter";
import omoiroRouter from "./routes/omoiroRouter";
import userRouter from "./routes/userRouter";

const app = Express();

app.use(Express.json());
app.use(Express.urlencoded({ extended: false }));
app.use('/', indexRouter);
app.use('/omoiros', omoiroRouter);
app.use('/users', userRouter);

exports.app = functions.https.onRequest(app);