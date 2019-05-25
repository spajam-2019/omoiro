import * as functions from 'firebase-functions';

exports.HelloWorld = functions.https.onRequest((req, res) => {
    res.send("hello world");
})
