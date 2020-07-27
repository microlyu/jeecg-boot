package org.jeecg.modules.iot.service;

import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.DeviceSensor;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IDeviceService extends IService<Device> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Device device,List<DeviceSensor> deviceSensorList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Device device,List<DeviceSensor> deviceSensorList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
