package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.iot.entity.Device;
import org.jeecg.modules.iot.entity.DeviceProbe;
import org.jeecg.modules.iot.mapper.DeviceProbeMapper;
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
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private DeviceProbeMapper deviceProbeMapper;
	
	@Override
	@Transactional
	public void saveMain(Device device, List<DeviceProbe> deviceProbeList) {
		deviceMapper.insert(device);
		if(deviceProbeList!=null && deviceProbeList.size()>0) {
			for(DeviceProbe entity:deviceProbeList) {
				//外键设置
				entity.setDeviceId(device.getId());
				deviceProbeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Device device,List<DeviceProbe> deviceProbeList) {
		deviceMapper.updateById(device);
		
		//1.先删除子表数据
		deviceProbeMapper.deleteByMainId(device.getId());
		
		//2.子表数据重新插入
		if(deviceProbeList!=null && deviceProbeList.size()>0) {
			for(DeviceProbe entity:deviceProbeList) {
				//外键设置
				entity.setDeviceId(device.getId());
				deviceProbeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		deviceProbeMapper.deleteByMainId(id);
		deviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			deviceProbeMapper.deleteByMainId(id.toString());
			deviceMapper.deleteById(id);
		}
	}
	
}
