package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.iot.entity.OnboardPoint;
import org.jeecg.modules.iot.mapper.OnboardPointMapper;
import org.jeecg.modules.iot.service.IOnboardPointService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 接入点配置表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class OnboardPointServiceImpl extends ServiceImpl<OnboardPointMapper, OnboardPoint> implements IOnboardPointService {

}
