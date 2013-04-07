package com.liangge.financesmanager.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * User: liangge
 * Date: 13-4-6
 * Time: 下午7:34
 */
public class XmlConfig {
    private Map<String, String> configMap;

    public XmlConfig(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public String getString(String str){
        return configMap.get(str);
    }
    public String getString(String str, String defaultValue){
        String value = configMap.get(str);
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value;
    }
}
