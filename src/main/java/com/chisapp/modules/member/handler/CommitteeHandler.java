package com.chisapp.modules.member.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.member.bean.Committee;
import com.chisapp.modules.member.service.CommitteeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/21 14:36
 * @Version 1.0
 */
@RequestMapping("/committee")
@RestController
public class CommitteeHandler {

    private CommitteeService committeeService;
    @Autowired
    public void setCommitteeService(CommitteeService committeeService) {
        this.committeeService = committeeService;
    }

    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("committee", committeeService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param committee
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid Committee committee, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        committeeService.save(committee);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param committee
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid Committee committee, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        committeeService.update(committee);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete(Committee committee) {
        committeeService.delete(committee.getId());
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param mrmTownshipName
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String mrmTownshipName,
            @RequestParam(required = false) String name){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = committeeService.getByCriteria(mrmTownshipName, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据乡镇(街道)ID 获取对应的 村(居委会)集合
     * @param mrmTownshipId
     * @return
     */
    @GetMapping("/getByMrmTownshipId")
    public List<Map<String, Object>> getByMrmTownshipId(Integer mrmTownshipId) {
        return committeeService.getByMrmTownshipId(mrmTownshipId);
    }


}
