package oit.is.offline.jinrou.controller;

import java.util.ArrayList;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.PathVariable;

import oit.is.offline.jinrou.model.Room;
import oit.is.offline.jinrou.model.Morning;
import oit.is.offline.jinrou.service.AsyncRoom;
import oit.is.offline.jinrou.model.RandomRole;
import oit.is.offline.jinrou.model.UserMapper;
import oit.is.offline.jinrou.model.User;
import oit.is.offline.jinrou.model.Vote;

@Controller
public class Controller1 {
  int count = 0;
  int[] countUser = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 誰が何票投票されたか
  int[] recountUser = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 誰が何票投票されたか(再投票用)
  int revoteflag = 0; // 再投票用フラグ
  int votecount = 0; // 投票した人数
  int num; // ルームにいる人数
  String voteduser; // 投票されたユーザー
  ArrayList<String> vote = new ArrayList<String>();
  int f1 = 0; // 占い師用のフラグ
  int f2 = 0; // 霊媒師用のフラグ
  int f3 = 0; // 騎士用のフラグ
  int f4 = 0; // 人狼用のフラグ
  int jflag = 0; // 最初の人狼用のフラグ
  String attackdeuser = ""; // 襲撃されたユーザー
  int countmorning = 0; // morning.htmlに入った人数
  int alive;
  int guard;

  @Autowired
  Room room;

  @Autowired
  Morning morning;

  @Autowired
  AsyncRoom acroom;

  @Autowired
  UserMapper userMapper;

  @GetMapping("/room")
  public String room(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    room.addUser(loginUser);
    model.addAttribute("room", room);
    return "room.html";
  }

  @GetMapping("/game")
  public String game(ModelMap model) {
    RandomRole random = new RandomRole();
    User user = new User();
    int i, ran;
    num = room.getUsers().size();

    count++;
    if (count == num) {
      for (i = 1; i <= num; i++) {
        ran = random.Random(num);
        user.setName("user" + i);
        user.setRan(ran);
        userMapper.update(user);
      }
      model.addAttribute("i", i);
    }
    return "game.html";
  }

  @GetMapping("/role")
  public String role(ModelMap model, Principal prin) {
    String name;
    String loginUser = prin.getName();

    name = userMapper.getUser(loginUser);
    model.addAttribute("rolename", name);
    if(name.equals("人狼") && num >= 6){
      String werewolf = userMapper.getwerewolf(loginUser);
      model.addAttribute("werewolf", werewolf);
    }
    return "game.html";
  }

  @GetMapping("/start")
  public SseEmitter Count() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.count(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("/time")
  public SseEmitter pushCount(Principal prin) {
    String user = "user1";
    String nowUser = prin.getName();
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.time(sseEmitter, nowUser);

    return sseEmitter;
  }

  @GetMapping("/vote")
  public String vote(ModelMap model) {
    int i;
    String username;
    morning.initUser(); // 初期化
    userMapper.initGuard(); // 初期化
    attackdeuser = ""; // 初期化
    jflag = 0; // 初期化
    vote = userMapper.getvote();
    for (i = 0; i < num; i++) {
      username = "user" + (i + 1);
      if (vote.get(i).equals(username)) {
        model.addAttribute("user" + (i + 1), username);
      }
    }
    return "vote.html";
  }

  @GetMapping("/voting") // 1回目の投票
  public String voting(ModelMap model) {
    Vote voting = new Vote();
    int death = voting.Voting(vote.size(), countUser); // 吊るされるユーザー

    if (death == -1) { // 再投票へ
      revoteflag = 1;
      String username;
      for (int i = 0; i < num; i++) {
        username = "user" + (i + 1);
        if (vote.get(i).equals(username)) {
          model.addAttribute("user" + (i + 1), username);
        }
      }

      model.addAttribute("revote", death);
    } else if (death <= 10) { // 吊るす人決定
      voteduser = "user" + death;
      model.addAttribute("death", voteduser);
    }
    return "vote.html";
  }

  @GetMapping("/revoting") // 再投票
  public String revoting(ModelMap model) {
    Vote voting = new Vote();
    int death = voting.Voting(vote.size(), recountUser); // 吊るされるユーザー

    if (death == -1) {
      model.addAttribute("endvote", death);
    } else if (death <= 10) {
      voteduser = "user" + death;
      model.addAttribute("death", voteduser);
    }
    return "vote.html";
  }

  @GetMapping("/night")
  public String night(ModelMap model, Principal prin) {
    String rolename;
    String loginUser = prin.getName();
    String username;
    int dead = 0;
    int dora;
    String name;
    int hcnt, jcnt;

    alive = userMapper.getvote().size();
    userMapper.vote(voteduser);
    hcnt = userMapper.getHumanAlive();
    jcnt = userMapper.getJinrouAlive();

    if(loginUser.equals(voteduser)){
      model.addAttribute("deathuser", 1);
    }else{
      model.addAttribute("deathuser", 2);
    }

    if (jcnt == 0)
      model.addAttribute("hwin", hcnt);
    else if (hcnt <= jcnt)
      model.addAttribute("jwin", jcnt);
    else {
      model.addAttribute("continue", hcnt);
      vote = userMapper.getvote();
      rolename = userMapper.getUser(loginUser);

      model.addAttribute("rolename", rolename);



      if (rolename.equals("占い師")) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("占い師")) {
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f1++; // 占い師がアクセスした回数
      }

      if (rolename.equals("霊媒師")) {
        vote = userMapper.getdead();
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          if (dora == 1) {
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f2++; // 霊媒師がアクセスした回数
      }

      if (rolename.equals("騎士")) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("騎士")) {
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f3++; // 騎士がアクセスした回数
      }

      if (rolename.equals("人狼") && jflag != 0) {
        f4 = 0;
        if (attackdeuser != "") {
          model.addAttribute("at", attackdeuser);
        } else {
          model.addAttribute("jinrou", jflag);
        }
      }

      if (rolename.equals("人狼") && jflag == 0) {
        for (int i = 0; i < num; i++) {
          dora = userMapper.getDora("user" + (i + 1));
          name = userMapper.getRole("user" + (i + 1));
          if (dora == 0 && !name.equals("人狼")) {
            username = "user" + (i + 1);
            model.addAttribute("user" + (i + 1), username);
          }
        }
        f4++; // 人狼がアクセスした回数
        jflag++;
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

  @GetMapping("/vote/{num}")
  public String user1(@PathVariable Integer num) {
    if (revoteflag == 0) {
      countUser[num - 1]++;
    } else {
      recountUser[num - 1]++;
    }
    return "vote.html";
  }

  @GetMapping("/fortune/{name}") // 占い師
  public String fortune(@PathVariable String name, ModelMap model) {
    int fortune = userMapper.fortune(name);
    model.addAttribute("fortune", fortune);
    model.addAttribute("syuzoku", fortune);
    model.addAttribute("continue", fortune);
    model.addAttribute("deathuser", 2);
    return "night.html";
  }

  @GetMapping("/shaman/{name}") // 霊媒師
  public String shaman(@PathVariable String name, ModelMap model) {
    int shaman = userMapper.shaman(name);
    model.addAttribute("shaman", shaman);
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

  @GetMapping("/morning")
  public String morning(Principal prin, ModelMap model) {
    int morningcount = 0;
    String loginUser = prin.getName();
    morning.addUser(loginUser);
    morningcount = morning.getUsers().size(); // morning.htmlにアクセスした人数
    alive = userMapper.getvote().size(); // ゲームに参加している人数

    if (morningcount >= alive) {
      guard = userMapper.getGuard(attackdeuser);
      if (guard == 0) {
        userMapper.werewolf(attackdeuser);
        model.addAttribute("name", attackdeuser);
        model.addAttribute("flag", 1);
      } else if (guard == 1) {
        model.addAttribute("flag", 2);
      }

      revoteflag = 0; // 再投票用フラグの初期化
      votecount = 0; // 投票した人数の初期化

      for (int i = 0; i < num; i++) {
        countUser[i] = 0; // 投票情報の初期化
        recountUser[i] = 0; // 投票情報の初期化（再投票用）
      }
    }
    return "morning.html";
  }

  @GetMapping("/noon")
  public String noon(ModelMap model, Principal prin) {
    int hcnt, jcnt;
    String loginUser = prin.getName();
    alive = userMapper.getvote().size();
    hcnt = userMapper.getHumanAlive();
    jcnt = userMapper.getJinrouAlive();
    if (jcnt == 0) {
      model.addAttribute("hwin", hcnt);
    } else if (hcnt <= jcnt) {
      model.addAttribute("jwin", jcnt);
    } else{
      model.addAttribute("continue", hcnt);
    }

    if(loginUser.equals(attackdeuser) && guard == 0){
      model.addAttribute("deathuser", 1);
    }else{
      model.addAttribute("deathuser", 2);
    }

    return "noon.html";
  }


}
