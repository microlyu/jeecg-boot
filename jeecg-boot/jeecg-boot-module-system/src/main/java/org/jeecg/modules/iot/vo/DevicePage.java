package org.jeecg.modules.iot.vo;

import java.util.List;
import org.jeecg.modules.iot.entity.Device;
import org.jeecg.modules.iot.entity.DeviceProbe;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Data
@ApiModel(value="iot_devicePage对象", description="设备")
public class DevicePage {

	/**主键*/
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
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15, dictTable = "iot_device_type", dicText = "type_name", dicCode = "id")
    @Dict(dictTable = "iot_device_type", dicText = "type_name", dicCode = "id")
	@ApiModelProperty(value = "设备类型")
	private java.lang.String deviceType;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
	@ApiModelProperty(value = "设备名称")
	private java.lang.String deviceName;
	/**设备地址*/
	@Excel(name = "设备地址", width = 15)
	@ApiModelProperty(value = "设备地址")
	private java.lang.String deviceIp;
	/**采样频率*/
	@Excel(name = "采样频率", width = 15)
	@ApiModelProperty(value = "采样频率")
	private java.lang.Integer collectInterval;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private java.lang.String deviceStatus;

	@ExcelCollection(name="设备探针")
	@ApiModelProperty(value = "设备探针")
	private List<DeviceProbe> deviceProbeList;

}
