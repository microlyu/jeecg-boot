package org.jeecg.modules.iot.service;

import org.jeecg.modules.iot.entity.DeviceType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Description: 设备类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IDeviceTypeService extends IService<DeviceType> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addDeviceType(DeviceType deviceType);
	
	/**修改节点*/
	void updateDeviceType(DeviceType deviceType) throws JeecgBootException;
	
	/**删除节点*/
	void deleteDeviceType(String id) throws JeecgBootException;

}
