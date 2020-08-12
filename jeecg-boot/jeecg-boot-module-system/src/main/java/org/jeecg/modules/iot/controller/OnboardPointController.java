package org.jeecg.modules.iot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.iot.entity.OnboardPoint;
import org.jeecg.modules.iot.service.IOnboardPointService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 接入点配置表
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Api(tags="接入点配置表")
@RestController
@RequestMapping("/org.jeecg.modules.demo.org.jeecg.modules.iot/onboardPoint")
@Slf4j
public class OnboardPointController extends JeecgController<OnboardPoint, IOnboardPointService> {
	@Autowired
	private IOnboardPointService onboardPointService;
	
	/**
	 * 分页列表查询
	 *
	 * @param onboardPoint
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "接入点配置表-分页列表查询")
	@ApiOperation(value="接入点配置表-分页列表查询", notes="接入点配置表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OnboardPoint onboardPoint,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OnboardPoint> queryWrapper = QueryGenerator.initQueryWrapper(onboardPoint, req.getParameterMap());
		Page<OnboardPoint> page = new Page<OnboardPoint>(pageNo, pageSize);
		IPage<OnboardPoint> pageList = onboardPointService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param onboardPoint
	 * @return
	 */
	@AutoLog(value = "接入点配置表-添加")
	@ApiOperation(value="接入点配置表-添加", notes="接入点配置表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OnboardPoint onboardPoint) {
		onboardPointService.save(onboardPoint);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param onboardPoint
	 * @return
	 */
	@AutoLog(value = "接入点配置表-编辑")
	@ApiOperation(value="接入点配置表-编辑", notes="接入点配置表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OnboardPoint onboardPoint) {
		onboardPointService.updateById(onboardPoint);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "接入点配置表-通过id删除")
	@ApiOperation(value="接入点配置表-通过id删除", notes="接入点配置表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		onboardPointService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "接入点配置表-批量删除")
	@ApiOperation(value="接入点配置表-批量删除", notes="接入点配置表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.onboardPointService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "接入点配置表-通过id查询")
	@ApiOperation(value="接入点配置表-通过id查询", notes="接入点配置表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OnboardPoint onboardPoint = onboardPointService.getById(id);
		if(onboardPoint==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(onboardPoint);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param onboardPoint
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OnboardPoint onboardPoint) {
        return super.exportXls(request, onboardPoint, OnboardPoint.class, "接入点配置表");
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
        return super.importExcel(request, response, OnboardPoint.class);
    }

}
