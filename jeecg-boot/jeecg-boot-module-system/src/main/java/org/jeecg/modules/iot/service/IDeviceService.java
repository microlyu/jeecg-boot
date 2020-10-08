package org.jeecg.modules.iot.service;

import org.jeecg.modules.iot.entity.DeviceProbe;
import org.jeecg.modules.iot.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
public interface IDeviceService extends IService<Device> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Device device,List<DeviceProbe> deviceProbeList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Device device,List<DeviceProbe> deviceProbeList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
