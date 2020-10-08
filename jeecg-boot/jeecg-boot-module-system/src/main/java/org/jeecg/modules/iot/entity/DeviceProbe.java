package org.jeecg.modules.iot.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备探针
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@ApiModel(value="iot_device对象", description="设备")
@Data
@TableName("iot_device_probe")
public class DeviceProbe implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**探针编号*/
	@Excel(name = "探针编号", width = 15)
	@ApiModelProperty(value = "探针编号")
	private java.lang.String probeNo;
	/**所属设备*/
	@ApiModelProperty(value = "所属设备")
	private java.lang.String deviceId;
	/**监控对象*/
	@Excel(name = "监控对象", width = 15, dictTable = "iot_monitor_object", dicText = "object_name", dicCode = "id")
	@ApiModelProperty(value = "监控对象")
	private java.lang.String monitorId;
	/**传感类型*/
	@Excel(name = "传感类型", width = 15, dicCode = "sensor_type")
	@ApiModelProperty(value = "传感类型")
	private java.lang.String sensorType;
}
