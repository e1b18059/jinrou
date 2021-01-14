package oit.is.offline.jinrou.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import oit.is.offline.jinrou.model.Room;
import oit.is.offline.jinrou.service.AsyncRoom;
import oit.is.offline.jinrou.model.UserMapper;

@Controller
public class ControllerNight {
  int num; // ルームにいる人数
  public int f1 = 0; // 占い師用のフラグ
  public int f2 = 0; // 霊媒師用のフラグ
  public int f3 = 0; // 騎士用のフラグ
  public int f4 = 0; // 人狼用のフラグ
  public int jflag = 0; // 最初の人狼用のフラグ
  public String attackdeuser = ""; // 襲撃されたユーザー

  @Autowired
  ControllerVote cv;

  @Autowired
  Room room;

  @Autowired
  AsyncRoom acroom;

  @Autowired
  UserMapper userMapper;

  @GetMapping("/night")
  public String night(ModelMap model, Principal prin) {
    String rolename;
    String loginUser = prin.getName();
    String username;
    int dora;
    String name;
    int hcnt, jcnt;

    acroom.resetTime(); // タイマーのリセット
    num = room.getUsers().size();
    userMapper.vote(cv.voteduser); // 投票されたユーザーの処刑
    hcnt = userMapper.getHumanAlive(); // 市民陣営の生きている人数
    jcnt = userMapper.getJinrouAlive(); // 人狼陣営の生きている人数

    if (loginUser.equals(cv.voteduser)) {
      model.addAttribute("deathuser", 1);
    } else {
      model.addAttribute("deathuser", 2);
    }

    if (jcnt == 0) {
      model.addAttribute("hwin", hcnt);
    } else if (hcnt <= jcnt) {
      model.addAttribute("jwin", jcnt);
    } else {
      model.addAttribute("continue", hcnt);

      rolename = userMapper.getRole(loginUser);
      model.addAttribute("rolename", rolename);
      model.addAttribute("username", loginUser);

      // 占い師がだれを占うか選択する用のリンク表示
      if (rolename.equals("占い師")) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("占い師")) { // 自分を選べないようにする
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f1++; // 占い師がアクセスした回数
      }

      // 霊媒師がだれを見るか選択する用のリンク表示
      if (rolename.equals("霊媒師")) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          if (dora == 1) {
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f2++; // 霊媒師がアクセスした回数
      }
      // 騎士がだれを守るか選択する用のリンク表示
      if (rolename.equals("騎士")) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("騎士")) { // 自分を選べないようにする
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f3++; // 騎士がアクセスした回数
      }

      // 人狼の2人用の処理
      if (rolename.equals("人狼") && jflag != 0) {
        f4 = 0; // ??
        if (attackdeuser != "") {
          model.addAttribute("at", attackdeuser);
        } else {
          model.addAttribute("jinrou", jflag);
        }
      }

      // 人狼がだれを襲撃するか選択する用のリンク表示
      if (rolename.equals("人狼") && jflag == 0) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("人狼")) { // 自分を選べないようにする
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        if (loginUser.equals(cv.voteduser)) {
        } else {
          f4++; // 人狼がアクセスした回数
          jflag++;
        }
      }

      model.addAttribute("flag1", f1);
      model.addAttribute("flag2", f2);
      model.addAttribute("flag3", f3);
      model.addAttribute("flag4", f4);

      if (f1 > 1) {
        f1 = 0; // 2回以上のアクセスでリセット
      }
      if (f2 > 1) {
        f2 = 0; // 2回以上のアクセスでリセット
      }
      if (f3 > 1) {
        f3 = 0; // 2回以上のアクセスでリセット
      }
    }
    return "night.html";
  }

  @GetMapping("/fortune/{name}") // 占い師
  public String fortune(@PathVariable String name, ModelMap model) {
    int fortune = userMapper.fortune(name);
    model.addAttribute("fortune", fortune);
    model.addAttribute("name", name);
    model.addAttribute("syuzoku", fortune);
    model.addAttribute("continue", fortune);
    model.addAttribute("deathuser", 2);
    return "night.html";
  }

  @GetMapping("/shaman/{name}") // 霊媒師
  public String shaman(@PathVariable String name, ModelMap model) {
    int shaman = userMapper.shaman(name);
    model.addAttribute("shaman", shaman);
    model.addAttribute("name", name);
    model.addAttribute("syuzoku", shaman);
    model.addAttribute("continue", shaman);
    model.addAttribute("deathuser", 2);
    return "night.html";
  }

  @GetMapping("/knight/{name}") // 騎士
  public String knight(@PathVariable String name, ModelMap model) {
    userMapper.knight(name);
    model.addAttribute("knight", name);
    model.addAttribute("continue", name);
    model.addAttribute("deathuser", 2);
    return "night.html";
  }

  @GetMapping("/werewolf/{name}") // 人狼
  public String werewolf(@PathVariable String name, ModelMap model) {
    attackdeuser = name;
    model.addAttribute("werewolf", name);
    model.addAttribute("continue", name);
    model.addAttribute("deathuser", 2);
    return "night.html";
  }

}
