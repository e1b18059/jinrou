package oit.is.offline.jinrou.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.offline.jinrou.model.Room;
import oit.is.offline.jinrou.model.PlayerNum;
import oit.is.offline.jinrou.service.AsyncRoom;
import oit.is.offline.jinrou.model.RandomRole;
import oit.is.offline.jinrou.model.UserMapper;
import oit.is.offline.jinrou.model.User;

@Controller
public class ControllerMain {
  int num; // ルームにいる人数
  int alive;
  int guard;
  int roomcount = 0; // room.htmlのアクセス回数

  @Autowired
  Room room;

  @Autowired
  PlayerNum playernum;

  @Autowired
  AsyncRoom acroom;

  @Autowired
  UserMapper userMapper;

  @Autowired
  ControllerNight cn;

  @Autowired
  ControllerVote cv;

  @GetMapping("/room")
  public String room(Principal prin, ModelMap model) {
    roomcount++;
    if (roomcount == 1) { // room.htmlに最初にアクセスしたときだけ
      room.initUser(); // roomのユーザ配列の初期化
      playernum.initUser(); // playernumのユーザ配列の初期化
      userMapper.initUser(); // ユーザネームの初期化
      userMapper.initDora(); // 生死情報の初期化
      acroom.resetTime(); // タイマーのリセット
      cv.revoteflag = 0; // 再投票用フラグの初期化
      cv.voteduser = ""; // 吊されるユーザーの初期化
      for (int i = 0; i < num; i++) {
        cv.countUser[i] = 0; // 投票情報の初期化
        cv.recountUser[i] = 0; // 投票情報の初期化（再投票用
      }
    }
    String loginUser = prin.getName();
    room.addUser(loginUser);
    model.addAttribute("room", room);

    return "room.html";
  }

  @GetMapping("/game")
  public String game(Principal prin, ModelMap model) {
    RandomRole random = new RandomRole();
    User user = new User();
    int ran;
    int playercount;
    String loginUser = prin.getName();
    num = room.getUsers().size(); // ゲームに参加している人数
    playernum.addUser(loginUser);
    playercount = playernum.getUsers().size(); // game.htmlにアクセスした人数

    if (playercount == num) {
      for (int i = 1; i <= num; i++) {
        if (i == 1) {
          random.initRondom();
        }
        ran = random.Random(num);
        user.setName("user" + i);
        user.setRan(ran);
        userMapper.update(user);
      }
      model.addAttribute("player", playercount); // なんでもいいから返す
    }

    return "game.html";
  }

  @GetMapping("/role")
  public String role(ModelMap model, Principal prin) {
    String rolename;
    String loginUser = prin.getName();

    rolename = userMapper.getRole(loginUser);
    model.addAttribute("rolename", rolename);
    model.addAttribute("username", loginUser);
    if (rolename.equals("人狼") && num >= 6) {
      String werewolf = userMapper.getwerewolf(loginUser);
      model.addAttribute("werewolf", werewolf);
    }
    model.addAttribute("player", 1);

    roomcount = 0; // room.htmlのアクセス回数の初期化

    return "game.html";
  }

  @GetMapping("/start")
  public SseEmitter Start() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.playercount(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("/time")
  public SseEmitter time(Principal prin) {
    String nowUser = prin.getName();
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.time(sseEmitter, nowUser);

    return sseEmitter;
  }

  @GetMapping("/morning")
  public String morning(Principal prin, ModelMap model) {
    int morningcount = 0;
    String loginUser = prin.getName();
    playernum.addUser(loginUser);
    morningcount = playernum.getUsers().size(); // morning.htmlにアクセスした人数
    alive = userMapper.getplayer().size(); // 生きている人数

    if (morningcount >= alive) {
      guard = userMapper.getGuard(cn.attackdeuser);
      if (guard == 0) {
        userMapper.werewolf(cn.attackdeuser);
        model.addAttribute("name", cn.attackdeuser);
        model.addAttribute("flag", 1);
      } else if (guard == 1) {
        model.addAttribute("flag", 2);
      }
    }
    cv.revoteflag = 0; // 再投票用フラグの初期化
    for (int i = 0; i < num; i++) {
      cv.countUser[i] = 0; // 投票情報の初期化
      cv.recountUser[i] = 0; // 投票情報の初期化（再投票用)
    }

    return "morning.html";
  }

  @GetMapping("/noon")
  public String noon(ModelMap model, Principal prin) {
    int hcnt, jcnt;
    String loginUser = prin.getName();
    String rolename;
    alive = userMapper.getplayer().size(); // 生きている人数
    hcnt = userMapper.getHumanAlive(); // 生きている市民陣営の人数
    jcnt = userMapper.getJinrouAlive(); // 生きている人狼陣営の人数

    if (jcnt == 0) {
      model.addAttribute("hwin", hcnt);
    } else if (hcnt <= jcnt) {
      model.addAttribute("jwin", jcnt);
    } else {
      model.addAttribute("continue", hcnt);
    }

    if (loginUser.equals(cn.attackdeuser) && guard == 0) {
      model.addAttribute("deathuser", 1);
    } else {
      model.addAttribute("deathuser", 2);
    }

    rolename = userMapper.getRole(loginUser);
    model.addAttribute("rolename", rolename);
    model.addAttribute("username", loginUser);

    if (rolename.equals("人狼") && num >= 6) {
      String werewolf = userMapper.getwerewolf(loginUser);
      model.addAttribute("werewolf", werewolf);
    }

    return "noon.html";
  }

}
