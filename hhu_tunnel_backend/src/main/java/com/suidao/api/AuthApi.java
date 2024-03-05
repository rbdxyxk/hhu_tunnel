package com.suidao.api;

import cn.hutool.http.server.HttpServerRequest;
import com.suidao.base.Result;
import org.springframework.web.bind.annotation.*;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthApi {
    @PostMapping("/login")
    public Result login(){
        Map<String, Object> map = new HashMap<>();
        //token admin
        map.put("token","admin");
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
