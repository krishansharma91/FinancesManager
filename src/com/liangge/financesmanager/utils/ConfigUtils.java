package com.liangge.financesmanager.utils;


import android.content.Context;
import android.content.res.XmlResourceParser;
import com.liangge.financesmanager.R;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: liangge
 * Date: 13-4-6
 * Time: 下午3:03
 */
public class ConfigUtils {

    /**
     * 获取DB配置文件
     * @param context
     * @return
     */
    public static XmlConfig getDbConfig(Context context){
        int id = R.xml.sql;
        Map<String, String> resultMap = getXmlResultMap(context.getResources().getXml(id));
        return new XmlConfig(resultMap);
    }

    private static Map<String, String> getXmlResultMap(XmlResourceParser xmlParser){
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            while(xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT){
                if(xmlParser.getEventType() == XmlResourceParser.START_TAG){
                    String id = xmlParser.getIdAttribute();
                    if(StringUtils.isBlank(id)){
                        xmlParser.next();
                        continue;
                    }
                    /*首选取value标签，没有的话再取标签内容*/
                    String value = xmlParser.getAttributeValue(null, "value");
                    String text = xmlParser.nextText();
                    if(StringUtils.isNotBlank(value)){
                        resultMap.put(id, value);
                    }else if(StringUtils.isNotBlank(text)){
                        resultMap.put(id, text);
                    }
                }
                xmlParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }
}
