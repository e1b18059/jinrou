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

import oit.is.offline.jinrou.model.Room;
import oit.is.offline.jinrou.service.AsyncRoom;
import oit.is.offline.jinrou.model.RandomRole;
import oit.is.offline.jinrou.model.UserMapper;
import oit.is.offline.jinrou.model.User;

@Controller
public class Controller1 {
  int count = 0;
  int counttime = 0;
  int[] countUser = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

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
    int num = room.getUsers().size();

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
    int num = room.getUsers().size();
    String nowUser = prin.getName();
    counttime++;
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.time(sseEmitter, nowUser);

    return sseEmitter;
  }

  @GetMapping("/vote")
  public String vote(ModelMap model) {
    int i;
    int num = room.getUsers().size();
    String username;
    ArrayList<String> vote = new ArrayList<String>();
    vote = userMapper.getvote();
    for (i = 0; i < num; i++) {
      username = "user" + (i + 1);
      if (vote.get(i).equals(username)) {
        model.addAttribute("user" + (i + 1), username);
      }
    }
    return "vote.html";
  }

  @GetMapping("/user1")
  public String user1() {
    countUser[0]++;
    System.out.println(countUser[0]);
    return "game.html";
  }

  @GetMapping("/user2")
  public String user2() {
    countUser[1]++;
    System.out.println(countUser[1]);
    return "game.html";
  }

  @GetMapping("/user3")
  public String user3() {
    countUser[2]++;
    return "game.html";
  }

  @GetMapping("/user4")
  public String user4() {
    countUser[3]++;
    return "game.html";
  }

  @GetMapping("/user5")
  public String user5() {
    countUser[4]++;
    return "game.html";
  }

  @GetMapping("/user6")
  public String user6() {
    countUser[5]++;
    return "game.html";
  }

  @GetMapping("/user7")
  public String user7() {
    countUser[6]++;
    return "game.html";
  }

  @GetMapping("/user8")
  public String user8() {
    countUser[7]++;
    return "game.html";
  }

  @GetMapping("/user9")
  public String user9() {
    countUser[8]++;
    return "game.html";
  }

  @GetMapping("/user10")
  public String user10() {
    countUser[9]++;
    return "game.html";
  }
}
