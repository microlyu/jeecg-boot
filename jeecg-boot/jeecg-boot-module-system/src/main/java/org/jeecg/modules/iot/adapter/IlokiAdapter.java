package org.jeecg.modules.iot.adapter;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.RestUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class IlokiAdapter {

    public JSONObject excuteCollectData(String deviceIp) {
        // http://192.168.8.25/api/slot/0/io/rtd
        String url = "http://" + deviceIp + "/api/slot/0/io/rtd";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json"));
        headers.add("Accept", "vdn.dac.v1");
        ResponseEntity<JSONObject> re = RestUtil.request(url, HttpMethod.GET, headers, null, null, JSONObject.class);
        return re.getBody();
    }

    public JSONObject mockCollcectData(String deviceIp) {
        String jsonStr = "{\n" +
                "\t\"slot\": 0,\n" +
                "\t\"io\": {\n" +
                "\t\t\"rtd\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 0,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 292,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 292,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 295,\n" +
                "\t\t\t\t\"rtdValueScaled\": 29.200001,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 29.200001,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 29.5,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 1,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 32767,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 0,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 0,\n" +
                "\t\t\t\t\"rtdValueScaled\": 32767,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 0,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 0,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 2,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 32767,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 0,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 0,\n" +
                "\t\t\t\t\"rtdValueScaled\": 32767,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 0,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 0,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 3,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 290,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 289,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 292,\n" +
                "\t\t\t\t\"rtdValueScaled\": 29,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 28.9,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 29.200001,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 4,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 290,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 289,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 292,\n" +
                "\t\t\t\t\"rtdValueScaled\": 29,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 28.9,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 29.200001,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"rtdIndex\": 5,\n" +
                "\t\t\t\t\"rtdSensorType\": 1,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultiplied\": 290,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMin\": 290,\n" +
                "\t\t\t\t\"rtdValueEngineeringMultipliedMax\": 293,\n" +
                "\t\t\t\t\"rtdValueScaled\": 29,\n" +
                "\t\t\t\t\"rtdValueScaledMin\": 29,\n" +
                "\t\t\t\t\"rtdValueScaledMax\": 29.299999,\n" +
                "\t\t\t\t\"rtdResetMinValue\": 0,\n" +
                "\t\t\t\t\"rtdResetMaxValue\": 0\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        return JSONObject.parseObject(jsonStr);
    }
}
