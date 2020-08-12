package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.iot.entity.Device;
import org.jeecg.modules.iot.entity.DeviceSensor;
import org.jeecg.modules.iot.mapper.DeviceSensorMapper;
import org.jeecg.modules.iot.mapper.DeviceMapper;
import org.jeecg.modules.iot.service.IDeviceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private DeviceSensorMapper deviceSensorMapper;
	
	@Override
	@Transactional
	public void saveMain(Device device, List<DeviceSensor> deviceSensorList) {
		deviceMapper.insert(device);
		if(deviceSensorList!=null && deviceSensorList.size()>0) {
			for(DeviceSensor entity:deviceSensorList) {
				//外键设置
				entity.setDeviceId(device.getId());
				deviceSensorMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Device device,List<DeviceSensor> deviceSensorList) {
		deviceMapper.updateById(device);
		
		//1.先删除子表数据
		deviceSensorMapper.deleteByMainId(device.getId());
		
		//2.子表数据重新插入
		if(deviceSensorList!=null && deviceSensorList.size()>0) {
			for(DeviceSensor entity:deviceSensorList) {
				//外键设置
				entity.setDeviceId(device.getId());
				deviceSensorMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		deviceSensorMapper.deleteByMainId(id);
		deviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			deviceSensorMapper.deleteByMainId(id.toString());
			deviceMapper.deleteById(id);
		}
	}
	
}
