package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /** 一覧を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("userlist", service.getUserList());
        // user/list.htmlに画面遷移
        return "user/list";
    }

    /** User登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        //User登録画面に遷移
        return "user/register";
    }

    /** User登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated User user ,BindingResult res,Model model) {
        //入力チェックエラーエラーあり
        if(res.hasErrors()) {
            return getRegister(user);
        }
        //User登録
        service.saveUser(user);
        return "redirect:/user/list";
    }

    /** User更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer id,User user,Model model) {
        //idがnullのときpostUser()から渡された引数のuserをセットする
        if(id == null) {
            model.addAttribute("user", user);
        }

        //idがnullではないときサービスから取得したUserをセットする
        if(id != null) {
        model.addAttribute("user", service.getUser(id));
        }
        return "user/update";
    }

    /** User更新処理 */
    @PostMapping("/update/{id}/")
    public String postUser(@Validated User user ,BindingResult res,Model model) {
        //入力チェックエラーあり
        if(res.hasErrors()) {
            return getUser(null,user,model);
        }

        //エラーなしUser登録
        service.saveUser(user);
        return "redirect:/user/list";
    }

    /** User削除処理 */
    @PostMapping(path="list",params="deleteRun")
    public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {
        service.deleteUser(idck);
        return "redirect:/user/list";
    }

}
