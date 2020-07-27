package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.DeviceSensor;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.mapper.DeviceSensorMapper;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.service.IDeviceSensorService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备传感器
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class DeviceSensorServiceImpl extends ServiceImpl<DeviceSensorMapper, DeviceSensor> implements IDeviceSensorService {
	
	@Autowired
	private DeviceSensorMapper deviceSensorMapper;
	
	@Override
	public List<DeviceSensor> selectByMainId(String mainId) {
		return deviceSensorMapper.selectByMainId(mainId);
	}
}
