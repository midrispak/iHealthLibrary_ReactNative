/**
 * Created by zhangxu on 16/11/20.
 */


import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Button,
    TouchableNativeFeedback,
    DeviceEventEmitter,
    ScrollView
} from 'react-native';


import {
    iHealthDeviceManagerModule,
    BP7SModule,
    BPProfileModule
} from 'ihealthlibrary-react-native'

import Log from './Log';


var styles = StyleSheet.create({
    container: {
        margin: 20,
        marginTop: 50
    },
    contentContainer: {
        height: 400,
    },
    // 按钮
    button: {
        height: 60,
        marginTop: 10,
        justifyContent: 'center', // 内容居中显示
        backgroundColor: '#eedddd',
        alignItems: 'center'
    },
    // 按钮文字
    buttonText: {
        fontSize: 18
    },
    cell: {
        marginTop: 10,
        height: 25,
        alignItems: 'flex-start',
        justifyContent: 'center', // 内容居中显示
        marginBottom: 5
    },
});

class TipView extends Component {
    constructor(props) {
        super(props)

        this.state = {
            tip: ''
        }

    }

    render() {
        return (
            <View>
                <Text>
                    Tip: {this.state.tip}
                </Text>
            </View>
        )
    }


}


let log = new Log;

export default class BP7SView extends Component {

    constructor(props) {
        super(props);

        var error_Listener = null;
        var connectionListener = null;
        var battery_Listerner = null;
        var getOffLineNum_Listerner = null;
        var getOffLineData_Listerner = null;
        var getFunctionInfo_Listerner = null;
        var setUnit_Listener = null;
        var setAngle_Listener = null;

    }

    componentWillMount() {
        log.info('BP7SView', 'componentWillMount()', "mac = " + this.props.mac + " type = " + this.props.type);
        this._addListener();
    }

    componentDidMount() {
        log.info('BP7SView', 'componentDidMount()', null);
    }


    componentWillReceiveProps() {
        log.info('BP7SView', 'componentWillReceiveProps()', null);
    }

    shouldComponentUpdate() {
        log.info('BP7SView', 'shouldComponentUpdate()', null);
    }

    componentWillUpdate() {
        log.info('BP7SView', 'componentWillUpdate()', null);
    }

    componentDidUpdate() {
        log.info('BP7SView', 'componentDidUpdate()', null);
    }

    componentWillUnmount() {
        log.info('BP7SView', 'componentWillUnmount()', null);

        this._removeListener();

    }

    render() {

        log.info('BP7SView', 'render()', null);

        return (
            <View style={styles.container}>

                <ScrollView style={styles.contentContainer}>
                    <TouchableNativeFeedback

                        onPress={() => this._getDeviceIDPS()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                获得IDPS
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback

                        onPress={() => this._getFunctionInfo()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                获得下位机信息
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback

                        onPress={() => this._getBattery()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                获得电量
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback
                        onPress={() => this._startMeasure()}>

                        <View style={styles.button}>

                            <Text style={styles.buttonText}>
                                获得离线数据数量
                            </Text>
                        </View>


                    </TouchableNativeFeedback>
                    <TouchableNativeFeedback

                        onPress={() => this._stopMeasure()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                获得离线数据
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback

                        onPress={() => this._setUnit()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                设置单位
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback

                        onPress={() => this._setAngle()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                设置角度
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                    <TouchableNativeFeedback

                        onPress={() => this._disConnect()}>

                        <View style={styles.button}>
                            <Text style={styles.buttonText}>
                                断开连接
                            </Text>
                        </View>


                    </TouchableNativeFeedback>

                </ScrollView>

                <TipView ref='tipView'/>


            </View>


        );


    }


    _addListener() {


        let self = this;
        this.error_Listener = DeviceEventEmitter.addListener(iHealthDeviceManagerModule.Action_Error, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_Error', JSON.stringify(e));
            self.refs.TipView.setState({tip: JSON.stringify(e)});
        });
        this.connectionListener = DeviceEventEmitter.addListener(iHealthDeviceManagerModule.DeviceDisconnect, function (e: Event) {
            log.info('BP7SView', '_addListener()_DeviceDisconnect', JSON.stringify(e));
            self.props.navigator.pop();
        });

        this.battery_Listerner = DeviceEventEmitter.addListener(BPProfileModule.Action_Battery, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_Battery', JSON.stringify(e));
            self.refs.tipView.setState({tip: JSON.stringify(e)})

        });
        this.getOffLineNum_Listerner = DeviceEventEmitter.addListener(BPProfileModule.Action_getOffLineDataNum, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_getOffLineDataNum', JSON.stringify(e));
            self.refs.tipView.setState({tip: JSON.stringify(e)})

        });

        this.getOffLineData_Listerner = DeviceEventEmitter.addListener(BPProfileModule.Action_getOffLineData, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_getOffLineData', JSON.stringify(e));
            self.refs.tipView.setState({tip: JSON.stringify(e)})

        });

        this.getFunctionInfo_Listerner = DeviceEventEmitter.addListener(BPProfileModule.Action_getFunctionInfo, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_getFunctionInfo', JSON.stringify(e));
            self.refs.tipView.setState({tip: JSON.stringify(e)})

        });

        this.setUnit_Listener = DeviceEventEmitter.addListener(BPProfileModule.Action_setUnitSuccess, function (e: Event) {
            log.info('BP7SView', '_addListener()Action_setUnitSuccess', JSON.stringify(e));
        });
        this.setAngle_Listener = DeviceEventEmitter.addListener(BPProfileModule.Action_setAngleSuccess, function (e: Event) {
            log.info('BP7SView', '_addListener()_Action_setAngleSuccess', JSON.stringify(e));
        });


    }

    _removeListener() {
        //Unregister  event
        if (this.error_Listener) {
            this.error_Listener.remove()
        }
        if (this.connectionListener) {
            this.connectionListener.remove()
        }
        if (this.battery_Listerner) {
            this.battery_Listerner.remove()
        }
        if (this.getOffLineNum_Listerner) {
            this.getOffLineNum_Listerner.remove()
        }
        if (this.getOffLineData_Listerner) {
            this.getOffLineData_Listerner.remove()
        }
        if (this.getFunctionInfo_Listerner) {
            this.getFunctionInfo_Listerner.remove()
        }
        if (this.setUnit_Listener) {
            this.setUnit_Listener.remove()
        }
        if (this.setAngle_Listener) {
            this.setAngle_Listener.remove()
        }
    }


    _getDeviceIDPS() {
        iHealthDeviceManagerModule.getDevicesIDPS(this.props.mac, (e) => {
            console.log('deviceInfo:' + JSON.stringify(e));
            this.refs.tipView.setState({tip: JSON.stringify(e)})
        })
    }

    _getFunctionInfo() {

        BP7SModule.getFunctionInfo(this.props.mac);
    }

    _startMeasure() {

        BP7SModule.getOffLineNum(this.props.mac);
    }

    _stopMeasure() {
        BP7SModule.getOffLineData(this.props.mac);
    }

    _getBattery() {
        BP7SModule.getBattery(this.props.mac);
    }

    _setUnit() {
        BP7SModule.setUnit(this.props.mac, 1);
    }

    _setAngle() {
        BP7SModule.angleSet(this.props.mac, 90, 0, 0, 0);
    }

    _disConnect() {
        BP7SModule.disconnect(this.props.mac);
    }
}