package com.wmhas.controller;

import com.wmhas.controller.request.UserInfoRequest;
import com.wmhas.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserServiceImpl userService;


    @Autowired
    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 这里应该是登录数据库的地方，即：在login处连接数据库，在logout处释放资源
     * 和vue前端login类似，在logout触发事件后向后端post内容，以表示资源的释放
     * @RequestBody: 接受前端传递给后端的json字符串中的数据(post方法),在类中自定义参数类型
     */
    @PostMapping("/login")
    ResponseEntity<Map<String, Object>> login(@RequestBody UserInfoRequest request) {
        return ResponseEntity.ok(userService.login(request.getId(), request.getPassword()));
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserInfoRequest request) {
        //System.out.println("\n"+request+"\n");
        return ResponseEntity.ok(userService.register(request.getId(), request.getPassword()));
    }

    @PostMapping("/registerInfo")
    ResponseEntity<?> registerInfo(@RequestBody UserInfoRequest request) {
        return ResponseEntity.ok(userService.registerinfo(request));
    }

   @PostMapping("/modifyUserInfo")
    ResponseEntity<?> modifyUserInfo(@RequestBody UserInfoRequest request) {
        System.out.println("\n"+request+"\n");
        return ResponseEntity.ok(userService.modifyUserInfo(request));
    }


    /*
    @GetMapping("/workerDataPanel")
    public ResponseEntity<?> getWorkerDataPanel(@RequestParam("id") String id,
                                                @RequestParam("type") String type) {
        switch (type) {
            case "doctor":
                return ResponseEntity.ok(userService.getDoctorDataPanel(id));
            case "h_nurse":
                return ResponseEntity.ok(userService.getHeadNurseDataPanel(id));
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
    */
}
