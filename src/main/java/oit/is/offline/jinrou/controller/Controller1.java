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
  int revoteflag = 0;
  int votecount = 0; // 投票した人数
  int num; // ルームにいる人数
  String voteduser; // 投票されたユーザー
  ArrayList<String> vote = new ArrayList<String>();
  int fortune = 0;
  int shaman = 0;
  int f1 = 0; // 占い師用のフラグ
  int f2 = 0; // 霊媒師用のフラグ

  @Autowired
  Room room;
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
    int alive = 0;
    int dora;

    userMapper.vote(voteduser);
    vote = userMapper.getvote();
    alive = num - vote.size();
    rolename = userMapper.getUser(loginUser);

    for (int i = 0; i < num; i++) {
      dora = userMapper.getDora("user" + (i + 1));
      if (dora == 0) {
        username = "user" + (i + 1);
        if (vote.get(i).equals(username)) {
          model.addAttribute("user" + (i + 1), username);
        }
      }
    }

    model.addAttribute("rolename", rolename);

    if (rolename.equals("占い師")) {
      f1++; // 占い師がアクセスした回数
    }

    if (rolename.equals("霊媒師")) {
      vote = userMapper.getdead();
      f2++; // 霊媒師がアクセスした回数
    }

    model.addAttribute("flag1", f1);
    model.addAttribute("flag2", f2);
    if (f1 > 1) {
      f1 = 0; // 2回以上のアクセスでリセット
    }
    if (f2 > 1) {
      f2 = 0; // 2回以上のアクセスでリセット
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
    return "night.html";
  }

  @GetMapping("/shaman/{name}") // 霊媒師
  public String shaman(@PathVariable String name, ModelMap model) {
    int shaman = userMapper.shaman(name);
    model.addAttribute("shaman", shaman);
    model.addAttribute("syuzoku", shaman);
    return "night.html";
  }

}
