package org.jeecg.modules.iot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 被监控的对象VO
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Data
@ApiModel(value="iot_monitor_object对象", description="监控对象VO")
public class MonitorVO {

    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    /**所属部门*/
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;

    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String objectName;

    /**LOGO*/
    @Excel(name = "LOGO", width = 15)
    @ApiModelProperty(value = "LOGO")
    private String objectLogo;

    /**状态*/
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String objectStatus;

    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String objectMemo;

    @ApiModelProperty(value = "当前温度")
    private Double currentTemp = new Double(0);

    @ApiModelProperty(value = "当前湿度")
    private Double currentHumidity = new Double(0);
}
