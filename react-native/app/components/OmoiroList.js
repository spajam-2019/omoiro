import React from 'react';
import { Text, View } from 'react-native';
import HttpClient from '../modules/HttpClient'

export class OmoiroList extends React.Component {
  state = {
    omoiros: []
  }
  componentWillMount() {
    this.http_client = new HttpClient()
    this.http_client.getOmoiros().then((res) => {
      this.setState({
        omoiros: res
      })
    })
  }

  renderOmoiros() {
    return this.state.omoiros.map(data => {
      return <Text>{data.text}</Text>;
    });
  }

  render() {
    return <View>{this.renderOmoiros()}</View>;
  }
}
