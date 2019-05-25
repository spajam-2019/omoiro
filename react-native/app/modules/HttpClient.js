import Axios from 'axios'

export default class HttpClient {
  constructor() {
    const url = 'https://us-central1-omoiro.cloudfunctions.net'
    this.axios = Axios.default.create({
      baseURL: `${url}/app/`,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
      responseType: 'json',
    })
  }

  async getOmoiros() {
    try {
      const res = await this.axios.get('omoiros/show')

      return res.data
    } catch (e) {
      return { error: 503 }
    }
  }
}
