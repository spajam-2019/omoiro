import * as functions from 'firebase-functions';

import * as Express from "express";

const app = Express();

const indexRouter = require("./routes/indexRouter");
app.use(Express.json());
app.use(Express.urlencoded({ extended: false }));
app.use('/', indexRouter);

exports.app = functions.https.onRequest(app);