package nefu.graduation_async.controller;

import lombok.RequiredArgsConstructor;
import nefu.graduation_async.dox.Department;
import nefu.graduation_async.dox.User;
import nefu.graduation_async.service.AdminService;
import nefu.graduation_async.vo.ResultVO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //查看所有用户
    @GetMapping("users")
    public Mono<ResultVO>userList(){
        return adminService.allUsers().map(ResultVO::success);
    }
    //添加用户并返回全部
    @PostMapping("users")
    public Mono<ResultVO>addUser(User user){
        return adminService.addUser(user).thenReturn(ResultVO.ok());
    }
    //重置用户密码
    @PutMapping("users/{account}")
    public Mono<ResultVO> initPassword(@PathVariable String account)
    {
        return adminService.initPassword(account).thenReturn(ResultVO.ok());
    }



    //查询全部部门
    @GetMapping("departments")
    public Mono<ResultVO> departmentList(){
        return adminService.listDepartment().map(ResultVO::success);
    }
    //添加部门并返回所有部门
    @PostMapping("departments")
    public Mono<ResultVO> addDepartment(Department department){
        return adminService.addDepartment(department)
                .then(adminService.listDepartment())
                .map(ResultVO::success);
    }
    //删除空部门并返回所有部门
    @DeleteMapping("department/{did}")
    public Mono<ResultVO> delDepartment(@PathVariable String did){
        return adminService.delDepartment(did)
                .then(adminService.listDepartment())
                .map(ResultVO::success);
    }


}
