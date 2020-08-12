package org.jeecg.modules.iot.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.iot.entity.DeviceType;
import org.jeecg.modules.iot.mapper.DeviceTypeMapper;
import org.jeecg.modules.iot.service.IDeviceTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements IDeviceTypeService {

	@Override
	public void addDeviceType(DeviceType deviceType) {
		if(oConvertUtils.isEmpty(deviceType.getPid())){
			deviceType.setPid(IDeviceTypeService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			DeviceType parent = baseMapper.selectById(deviceType.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(deviceType);
	}
	
	@Override
	public void updateDeviceType(DeviceType deviceType) {
		DeviceType entity = this.getById(deviceType.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = deviceType.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				deviceType.setPid(IDeviceTypeService.ROOT_PID_VALUE);
			}
			if(!IDeviceTypeService.ROOT_PID_VALUE.equals(deviceType.getPid())) {
				baseMapper.updateTreeNodeStatus(deviceType.getPid(), IDeviceTypeService.HASCHILD);
			}
		}
		baseMapper.updateById(deviceType);
	}
	
	@Override
	public void deleteDeviceType(String id) throws JeecgBootException {
		DeviceType deviceType = this.getById(id);
		if(deviceType==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(deviceType.getPid());
		baseMapper.deleteById(id);
	}
	
	
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IDeviceTypeService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<DeviceType>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IDeviceTypeService.NOCHILD);
			}
		}
	}

}
