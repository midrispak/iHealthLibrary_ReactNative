'use strict';


// import { NativeModules } from 'react-native';

// export default NativeModules.iHealthDeviceManagerModel;


// var { NativeModules } = require('react-native');

// module.exports = NativeModules;

var Component = {
    iHealthDeviceManagerModule: require('./Module/iHealthDeviceManagerModule'),
    BP5Module: require('./Module/BP5Module'),
    BP3LModule: require('./Module/BP3LModule'),
    BP550BTModule: require('./Module/BP550BTModule'),
    BP7SModule: require('./Module/BP7SModule'),
    BPProfileModule: require('./Module/BPProfileModule'),
    AM3SModule: require('./Module/AM3SModule'),
    AM4Module: require('./Module/AM4Module'),
    AMProfileModule: require('./Module/AMProfileModule'),
    PO3Module: require('./Module/PO3Module'),
    POProfileModule: require('./Module/POProfileModule'),
    HS4SModule: require('./Module/HS4SModule'),
    HSProfileModule: require('./Module/HSProfileModule'),
    BG1Module: require('./Module/BG1Module'),
    BG1ProfileModule: require('./Module/BG1ProfileModule')

}

module.exports = Component;