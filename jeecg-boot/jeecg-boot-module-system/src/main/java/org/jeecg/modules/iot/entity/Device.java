package org.jeecg.modules.iot.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@ApiModel(value="iot_device对象", description="设备表")
@Data
@TableName("iot_device")
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15, dictTable = "iot_device_type", dicText = "type_name", dicCode = "id")
    @Dict(dictTable = "iot_device_type", dicText = "type_name", dicCode = "id")
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
	@Excel(name = "接入点", width = 15, dictTable = "iot_onboard_point", dicText = "onboard_name", dicCode = "id")
    @Dict(dictTable = "iot_onboard_point", dicText = "onboard_name", dicCode = "id")
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
}
