package org.jeecg.modules.iot.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 接入点配置表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
@TableName("iot_onboard_point")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="iot_onboard_point对象", description="接入点配置表")
public class OnboardPoint implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**接入点名称*/
	@Excel(name = "接入点名称", width = 15)
    @ApiModelProperty(value = "接入点名称")
    private String onboardName;
	/**接入点协议*/
	@Excel(name = "接入点协议", width = 15, dicCode = "protocol_type")
	@Dict(dicCode = "protocol_type")
    @ApiModelProperty(value = "接入点协议")
    private String protocol;
	/**访问地址*/
	@Excel(name = "访问地址", width = 15)
    @ApiModelProperty(value = "访问地址")
    private String accessUrl;
	/**主动拉取的采集间隔（秒）*/
	@Excel(name = "主动拉取的采集间隔（秒）", width = 15)
    @ApiModelProperty(value = "主动拉取的采集间隔（秒）")
    private Integer observeInterval;
	/**认证账户*/
	@Excel(name = "认证账户", width = 15)
    @ApiModelProperty(value = "认证账户")
    private String authAccount;
	/**认证密码*/
	@Excel(name = "认证密码", width = 15)
    @ApiModelProperty(value = "认证密码")
    private String authPassword;
	/**参数一*/
	@Excel(name = "参数一", width = 15)
    @ApiModelProperty(value = "参数一")
    private String param1;
	/**参数2*/
	@Excel(name = "参数2", width = 15)
    @ApiModelProperty(value = "参数2")
    private String param2;
	/**参数3*/
	@Excel(name = "参数3", width = 15)
    @ApiModelProperty(value = "参数3")
    private String param3;
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
