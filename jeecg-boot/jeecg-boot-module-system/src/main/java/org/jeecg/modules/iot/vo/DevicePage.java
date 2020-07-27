package org.jeecg.modules.iot.vo;

import java.util.List;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.Device;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.DeviceSensor;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
@ApiModel(value="iot_devicePage对象", description="设备表")
public class DevicePage {

	/**主键*/
	@ApiModelProperty(value = "主键")
	private String id;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15)
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	/**设备序列号*/
	@Excel(name = "设备序列号", width = 15)
	@ApiModelProperty(value = "设备序列号")
	private String deviceSn;
	/**设备图片*/
	@Excel(name = "设备图片", width = 15)
	@ApiModelProperty(value = "设备图片")
	private String devicePic;
	/**接入点*/
	@Excel(name = "接入点", width = 15)
	@ApiModelProperty(value = "接入点")
	private String onboadPoint;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private String sysOrgCode;
	
	@ExcelCollection(name="设备传感器")
	@ApiModelProperty(value = "设备传感器")
	private List<DeviceSensor> deviceSensorList;
	
}
