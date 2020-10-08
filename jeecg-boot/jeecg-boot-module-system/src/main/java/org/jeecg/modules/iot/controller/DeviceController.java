package org.jeecg.modules.iot.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.prometheus.client.Collector;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.iot.adapter.IlokiAdapter;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.iot.entity.DeviceProbe;
import org.jeecg.modules.iot.entity.Device;
import org.jeecg.modules.iot.vo.DevicePage;
import org.jeecg.modules.iot.service.IDeviceService;
import org.jeecg.modules.iot.service.IDeviceProbeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2020-10-06
 * @Version: V1.0
 */
@Api(tags = "设备")
@RestController
@RequestMapping("/iot/device")
@Slf4j
public class DeviceController {
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IDeviceProbeService deviceProbeService;
    @Autowired
    private IlokiAdapter lokiAdapter;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 分页列表查询
     *
     * @param device
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备-分页列表查询")
    @ApiOperation(value = "设备-分页列表查询", notes = "设备-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(Device device,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
        Page<Device> page = new Page<Device>(pageNo, pageSize);
        IPage<Device> pageList = deviceService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param devicePage
     * @return
     */
    @AutoLog(value = "设备-添加")
    @ApiOperation(value = "设备-添加", notes = "设备-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevicePage devicePage) {
        Device device = new Device();
        BeanUtils.copyProperties(devicePage, device);
        deviceService.saveMain(device, devicePage.getDeviceProbeList());
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devicePage
     * @return
     */
    @AutoLog(value = "设备-编辑")
    @ApiOperation(value = "设备-编辑", notes = "设备-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevicePage devicePage) {
        Device device = new Device();
        BeanUtils.copyProperties(devicePage, device);
        Device deviceEntity = deviceService.getById(device.getId());
        if (deviceEntity == null) {
            return Result.error("未找到对应数据");
        }
        deviceService.updateMain(device, devicePage.getDeviceProbeList());
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备-通过id删除")
    @ApiOperation(value = "设备-通过id删除", notes = "设备-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        deviceService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设备-批量删除")
    @ApiOperation(value = "设备-批量删除", notes = "设备-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.deviceService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备-通过id查询")
    @ApiOperation(value = "设备-通过id查询", notes = "设备-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        Device device = deviceService.getById(id);
        if (device == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(device);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备探针通过主表ID查询")
    @ApiOperation(value = "设备探针主表ID查询", notes = "设备探针-通主表ID查询")
    @GetMapping(value = "/queryDeviceProbeByMainId")
    public Result<?> queryDeviceProbeListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<DeviceProbe> deviceProbeList = deviceProbeService.selectByMainId(id);
        return Result.ok(deviceProbeList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param device
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Device device) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<Device> queryList = deviceService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<Device> deviceList = new ArrayList<Device>();
        if (oConvertUtils.isEmpty(selections)) {
            deviceList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            deviceList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<DevicePage> pageList = new ArrayList<DevicePage>();
        for (Device main : deviceList) {
            DevicePage vo = new DevicePage();
            BeanUtils.copyProperties(main, vo);
            List<DeviceProbe> deviceProbeList = deviceProbeService.selectByMainId(main.getId());
            vo.setDeviceProbeList(deviceProbeList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "设备列表");
        mv.addObject(NormalExcelConstants.CLASS, DevicePage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备数据", "导出人:" + sysUser.getRealname(), "设备"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<DevicePage> list = ExcelImportUtil.importExcel(file.getInputStream(), DevicePage.class, params);
                for (DevicePage page : list) {
                    Device po = new Device();
                    BeanUtils.copyProperties(page, po);
                    deviceService.saveMain(po, page.getDeviceProbeList());
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

    /**
     * 获取设备在Redis中的probes
     *
     * @param deviceIp
     * @return
     */
    @GetMapping(value = "/checkRemoteAddress")
    public Result<?> checkRemoteAddress(@RequestParam(name = "deviceIp", required = true) String deviceIp) {
        // 远程调用设备IP地址，并获取返回
        // 解析获取的报文，并将当前的probe信息以及温度信息放入redis
        List<DictModel> probeList = new ArrayList<DictModel>();
//		 probeList.add(new DictModel("001", "001"));
//		 probeList.add(new DictModel("002", "002"));
//		 probeList.add(new DictModel("003", "003"));
        JSONObject oMetricsData = lokiAdapter.mockCollcectData(deviceIp);
        JSONArray array = oMetricsData.getJSONObject("io").getJSONArray("rtd");
        for (int i = 0; i < array.size(); i++) {
            JSONObject item = array.getJSONObject(i);
            String itemIndex = item.getString("rtdIndex");
            String itemValue = item.getString("rtdValueScaled");
            probeList.add(new DictModel(itemIndex, itemIndex));
        }
        return Result.ok(probeList);
    }

    @RequestMapping(value = "/{deviceId}/metrics", method = RequestMethod.GET)
    public String metrics(@PathVariable String deviceId) {

    	StringBuffer buffer = new StringBuffer();
        List<String> labelNames = Arrays.asList("deviceId", "objectId", "probeNo");

        Device device = deviceService.getById(deviceId);
        List<DeviceProbe> probeList = deviceProbeService.selectByMainId(deviceId);
        JSONObject oMetricsData = lokiAdapter.mockCollcectData(device.getDeviceIp());

        JSONArray array = oMetricsData.getJSONObject("io").getJSONArray("rtd");
        for (int i = 0; i < array.size(); i++) {
            JSONObject item = array.getJSONObject(i);
            String itemIndex = item.getString("rtdIndex");
            double itemValue = item.getDoubleValue("rtdValueScaled");
            System.out.println("jsonvalue is " + itemIndex + " - " + itemValue);

            for (DeviceProbe probeItem : probeList) {
                if (itemIndex.equalsIgnoreCase(probeItem.getProbeNo())) {
                    List<String> labelValues = Arrays.asList(deviceId, probeItem.getMonitorId(), probeItem.getProbeNo());
                    Collector.MetricFamilySamples.Sample sample = new Collector.MetricFamilySamples.Sample(probeItem.getSensorType(), labelNames, labelValues, itemValue);
                    buffer.append(convertSample2Str(sample));
                    String redisKey = "monitor:" + probeItem.getMonitorId() + ":" + probeItem.getSensorType();
                    redisUtil.set(redisKey, itemValue);
                }
            }
        }
        System.out.println(buffer.toString());
        return buffer.toString();
    }

    private String convertSample2Str(Collector.MetricFamilySamples.Sample data) {
    	StringBuffer buffer = new StringBuffer();
		buffer.append("# TYPE ").append(data.name).append(" gauge \r\n");
		buffer.append(data.name).append("{");
		for (int i = 0; i < data.labelNames.size(); i++) {
			if (i>0) {
				buffer.append(",");
			}
			buffer.append(data.labelNames.get(i)).append("=\"").append(data.labelValues.get(i)).append("\"");
		}
		buffer.append("} ").append(data.value).append("\r\n");
		return buffer.toString();
	}
}
