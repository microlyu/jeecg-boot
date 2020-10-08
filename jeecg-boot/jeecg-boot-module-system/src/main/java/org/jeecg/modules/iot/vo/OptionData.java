package org.jeecg.modules.iot.vo;

import lombok.Data;

@Data
public class OptionData {



    private String text;
    private String value;

    public OptionData() {
    }

    public OptionData(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
