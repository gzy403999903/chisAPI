package com.chisapp.modules.member.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.component.SimpleModelAttribute;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.member.bean.Member;
import com.chisapp.modules.member.bean.MemberHealth;
import com.chisapp.modules.member.service.MemberService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/23 15:17
 * @Version 1.0
 */
@RequestMapping("/member")
@RestController
public class MemberHandler {

    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Value("${file.member-head-image-dir}")
    private String memberHeadImageDir;

    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     *
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getById(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("member", memberService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param member
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid Member member, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        memberService.save(member);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param member
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid Member member, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        memberService.update(member);
        return PageResult.success();
    }

    /**
     * 编辑会员健康档案操作
     * @return
     */
    @PutMapping("/updateHealthArchive")
    public PageResult updateHealthArchive(@RequestBody String mapJson) {
        Map<String, String> map =
                JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("mrmMemberJson") == null || map.get("mrmMemberHealthJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        String mrmMemberJson = map.get("mrmMemberJson");
        Map<String, Object> memberMap =
                JSONUtils.parseJsonToObject(mrmMemberJson, new TypeReference<Map<String, Object>>() {});
        Integer id = (int) memberMap.get("id");
        Member member = this.memberService.getById(id);
        Member memberRecord = this.memberService.getById(id);
        if (member == null) {
            throw new RuntimeException("无法获取更新对象");
        }
        SimpleModelAttribute.updateValues(memberMap, member);

        String mrmMemberHealthJson = map.get("mrmMemberHealthJson");
        List<MemberHealth> memberHealthList =
                JSONUtils.parseJsonToObject(mrmMemberHealthJson, new TypeReference<List<MemberHealth>>() {});

        memberService.updateHealthArchive(member, memberRecord, memberHealthList);
        return PageResult.success();
    }

    /**
     * 删除操作
     *
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete(Member member) {
        memberService.delete(member);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param phone
     * @param idCardNo
     * @param state
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "1") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String idCardNo,
            @RequestParam(required = false) Boolean state) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = memberService.getByCriteria(name, phone, idCardNo, state);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据查询条件进行分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param phone
     * @param idCardNo
     * @return
     */
    @GetMapping("/getByCriteriaForHealth")
    public PageResult getByCriteriaForHealth(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "1") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String idCardNo,
            @RequestParam(required = false) String healthState) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = memberService.getByCriteriaForHealth(name, phone, idCardNo, healthState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据会员ID 获取对应的会员健康概况
     *
     * @param id
     * @return
     */
    @GetMapping("/getByIdForTreatment")
    public PageResult getByIdForTreatment(@RequestParam("id") Integer id) {
        Map<String, Object> member = memberService.getByIdForTreatment(id);
        return PageResult.success().resultSet("member", member);
    }

    /**
     * 根据关键字检索启用状态会员
     *
     * @param keyword
     * @return
     */
    @GetMapping("/getEnabledLikeByKeyword")
    public List<Map<String, Object>> getEnabledLikeByKeyword(@RequestParam("keyword") String keyword) {
        if (keyword.trim().equals("")) {
            return null;
        }
        return memberService.getEnabledLikeByKeyword(keyword);
    }


    @PostMapping("/uploadMemberImage")
    public PageResult uploadMemberHeadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 设置文件名
        String fileName = file.getOriginalFilename();
        // 设置存放路径
        ServletContext servletContext = request.getSession().getServletContext();
        String path = servletContext.getRealPath(memberHeadImageDir + fileName);
        File dest = new File(path);

        // 如果路径不存在则自动创建
        if (!dest.exists()) {
            dest.mkdirs();
        }

        try {
            // 保存文件
            file.transferTo(dest);
        } catch (IOException e) {
            return PageResult.fail().msg(e.getMessage());
        }

        return PageResult.success();
    }


}
