package com.ruoyi.web.controller.zb;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ZbTable1;
import com.ruoyi.system.service.IZbTable1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业基本信息Controller
 * 
 * @author CA
 * @date 2024-04-28
 */
@RestController
@RequestMapping("/zb/table1")
public class ZbTable1Controller extends BaseController
{
    @Autowired
    private IZbTable1Service zbTable1Service;

    /**
     * 查询企业基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZbTable1 zbTable1)
    {
        startPage();
        List<ZbTable1> list = zbTable1Service.selectZbTable1List(zbTable1);
        return getDataTable(list);
    }

    /**
     * 导出企业基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:export')")
    @Log(title = "企业基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZbTable1 zbTable1)
    {
        List<ZbTable1> list = zbTable1Service.selectZbTable1List(zbTable1);
        ExcelUtil<ZbTable1> util = new ExcelUtil<ZbTable1>(ZbTable1.class);
        util.exportExcel(response, list, "企业基本信息数据");
    }

    /**
     * 获取企业基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return success(zbTable1Service.selectZbTable1ByCompanyId(companyId));
    }

    /**
     * 新增企业基本信息
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:add')")
    @Log(title = "企业基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZbTable1 zbTable1)
    {
        return toAjax(zbTable1Service.insertZbTable1(zbTable1));
    }

    /**
     * 修改企业基本信息
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:edit')")
    @Log(title = "企业基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZbTable1 zbTable1)
    {
        return toAjax(zbTable1Service.updateZbTable1(zbTable1));
    }

    /**
     * 删除企业基本信息
     */
    @PreAuthorize("@ss.hasPermi('zb:table1:remove')")
    @Log(title = "企业基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds)
    {
        return toAjax(zbTable1Service.deleteZbTable1ByCompanyIds(companyIds));
    }
}
