import React from 'react';
import { Text } from 'react-native';
import HttpClient from '../modules/HttpClient'

export class OmoiroCreateForm extends React.Component {
  state = {
    status: 'nothing'
  }
  componentWillMount() {
    this.http_client = new HttpClient()
    this.http_client.postOmoiro().then((res) => {
      this.setState({
        status: res.error
      })
    })
  }

  render() {
    return <Text>{this.state.status}</Text>;
  }
}
