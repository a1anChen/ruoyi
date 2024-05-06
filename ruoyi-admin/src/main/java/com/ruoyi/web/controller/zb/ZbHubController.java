package com.ruoyi.web.controller.zb;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
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
import com.ruoyi.system.domain.ZbHub;
import com.ruoyi.system.service.IZbHubService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 指标管理Controller
 * 
 * @author CA
 * @date 2024-04-27
 */
@RestController
@RequestMapping("/zb/zbHub")
public class ZbHubController extends BaseController
{
    @Autowired
    private IZbHubService zbHubService;

    /**
     * 查询指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:list')")
    @GetMapping("/list")
    public AjaxResult list(ZbHub zbHub)
    {
        List<ZbHub> list = zbHubService.selectZbHubList(zbHub);
        return success(list);
    }

    /**
     * 企业基本情况的二级指标
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:list')")
    @GetMapping("/listSecond1")
    public R<List<ZbHub>> listSecond1(ZbHub zbHub)
    {
        List<ZbHub> list = zbHubService.selectZbHubList(zbHub);
        List<ZbHub> rList = new ArrayList<>();

        for (ZbHub zbIndex: list){
            if (zbIndex.getParentId() == 1){
                rList.add(zbIndex);
            }
        }
        return R.ok(rList);
    }

    /**
     * 导出指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:export')")
    @Log(title = "指标管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZbHub zbHub)
    {
        List<ZbHub> list = zbHubService.selectZbHubList(zbHub);
        ExcelUtil<ZbHub> util = new ExcelUtil<ZbHub>(ZbHub.class);
        util.exportExcel(response, list, "指标管理数据");
    }

    /**
     * 获取指标管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:query')")
    @GetMapping(value = "/{indexId}")
    public AjaxResult getInfo(@PathVariable("indexId") Long indexId)
    {
        return success(zbHubService.selectZbHubByIndexId(indexId));
    }

    /**
     * 新增指标管理
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:add')")
    @Log(title = "指标管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZbHub zbHub)
    {
        return toAjax(zbHubService.insertZbHub(zbHub));
    }

    /**
     * 修改指标管理
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:edit')")
    @Log(title = "指标管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZbHub zbHub)
    {
        return toAjax(zbHubService.updateZbHub(zbHub));
    }

    /**
     * 删除指标管理
     */
    @PreAuthorize("@ss.hasPermi('zb:zbHub:remove')")
    @Log(title = "指标管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{indexIds}")
    public AjaxResult remove(@PathVariable Long[] indexIds)
    {
        return toAjax(zbHubService.deleteZbHubByIndexIds(indexIds));
    }
}
