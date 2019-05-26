export function objectToArray(obj: any) {
    return Object.keys(obj).map(x => { obj[x].id = x; return obj[x] })
}