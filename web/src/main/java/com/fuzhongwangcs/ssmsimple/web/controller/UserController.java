package com.fuzhongwangcs.ssmsimple.web.controller;

import com.fuzhongwangcs.ssmsimple.web.model.Role;
import com.fuzhongwangcs.ssmsimple.web.model.User;
import com.fuzhongwangcs.ssmsimple.web.model.UserRole;
import com.fuzhongwangcs.ssmsimple.web.security.PermissionSign;
import com.fuzhongwangcs.ssmsimple.web.service.RoleService;
import com.fuzhongwangcs.ssmsimple.web.service.UserRoleService;
import com.fuzhongwangcs.ssmsimple.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.fuzhongwangcs.ssmsimple.web.security.RoleSign.ADMIN;

/**
 * @Author: lazyeclipse
 * @Description: 用户控制器
 * @Date: 2017/5/31 11:54
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 用户登录
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, BindingResult result, Model model, HttpServletRequest request, HttpSession session) {
        session.removeAttribute("userInfo");
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
                return "redirect:/";
            }
            if (result.hasErrors()) {
                model.addAttribute("error", "参数错误！");
                return "login";
            }
            // 身份验证
            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            subject.checkRole(ADMIN);
            // 验证成功在Session中保存用户信息
            final User authUserInfo = userService.selectByUsername(user.getUsername());
            request.getSession().setAttribute("userInfo", authUserInfo);
        } catch (AuthenticationException e) {
            // 身份验证失败
            model.addAttribute("error", "用户名或密码错误 ！");
            return "login";
        }
        return "index";
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 基于角色 标识的权限控制案例
     * 判断角色
     */
    @RequestMapping(value = "/admin")
    @ResponseBody
    @RequiresRoles(value = ADMIN)
    public String admin() {
        return "拥有admin角色,能访问";
    }

    /**
     * 基于权限标识的权限控制案例
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    @RequiresPermissions(value = PermissionSign.USER_CREATE)
    public String create() {
        return "拥有user:create权限,能访问";
    }

    /**
     * 基于角色 标识的权限控制案例
     * 添加角色
     */
    @RequestMapping(value = "/addrole")
    @ResponseBody
    @RequiresRoles(value = ADMIN)
    public String addrole(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        String roleid = request.getParameter("roleid");
        User user = userService.selectById(Long.valueOf(userid));
        if (user != null) {
            List<Role> roles = roleService.selectRolesByUserId(Long.valueOf(userid));
            boolean isexist = false;
            for (Role role : roles) {
                if (role.getId() == Long.valueOf(roleid))
                    isexist = true;
            }
            if (isexist)
                return "用户已有此权限，请勿重新赋予~";
            else {
                UserRole userRole = new UserRole();
                userRole.setUserId(Long.valueOf(userid));
                userRole.setRoleId(Long.valueOf(roleid));
                userRoleService.insert(userRole);
            }
        } else {
            return "用户不存在！";
        }
        return "拥有user:create权限,能访问";
    }
}
