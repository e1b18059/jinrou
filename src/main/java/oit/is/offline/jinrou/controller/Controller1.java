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


@Controller
public class Controller1{

  @Autowired
  private Room room;
  @Autowired
  AsyncRoom acroom;

  @GetMapping("/room")
  public String room(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    return "room.html";
  }

  @GetMapping("game")
  public String game(){
    return "game.html";
  }

  @GetMapping("/start")
  public SseEmitter pushCount() {
    System.out.println("pushCount");

    // push処理の秘密兵器．これを利用してブラウザにpushする
    // finalは初期化したあとに再代入が行われない変数につける（意図しない再代入を防ぐ）
    final SseEmitter sseEmitter = new SseEmitter();
    this.acroom.count(sseEmitter);
    return sseEmitter;
  }

}
