package oit.is.offline.jinrou.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.offline.jinrou.model.Room;
import oit.is.offline.jinrou.model.PlayerNum;
import oit.is.offline.jinrou.model.UserMapper;
import oit.is.offline.jinrou.model.Vote;
import oit.is.offline.jinrou.service.AsyncRoom;

@Controller
public class ControllerVote {
  public int[] countUser = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 誰が何票投票されたか
  public int[] recountUser = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 誰が何票投票されたか(再投票用)
  public int revoteflag = 0; // 再投票用フラグ
  int num; // ルームにいる人数
  public String voteduser; // 投票されたユーザー
  ArrayList<String> aliveplayer = new ArrayList<String>(); // 生きているユーザーのユーザー名
  int apnum;
  public int votecount = 0;
  public  int flag = 0;

  @Autowired
  Room room;

  @Autowired
  UserMapper userMapper;

  @Autowired
  PlayerNum playernum;

  @Autowired
  ControllerNight cn;

  @Autowired
  AsyncRoom acroom;

  @GetMapping("/vote")
  public String vote(ModelMap model) {
    String username;

    cn.attackdeuser = ""; // 初期化
    cn.jflag = 0; // 初期化
    cn.f1 = 0; // 各役職のフラグ初期化
    cn.f2 = 0;
    cn.f3 = 0;
    cn.f4 = 0;
    playernum.initUser();
    userMapper.initGuard(); // 初期化

    aliveplayer = userMapper.getplayer();
    apnum = aliveplayer.size();
    num = room.getUsers().size();
    for (int i = 0; i < num; i++) {
      int dora = userMapper.getDora("user" + (i + 1));
      if (dora == 0) {
        username = "user" + (i + 1);
        model.addAttribute("user" + (i + 1), username);
      }
    }

    return "vote.html";
  }

  @GetMapping("/voting") // 1回目の投票
  public String voting(ModelMap model) {
    Vote voting = new Vote();
    int voted = voting.Voting(countUser); // 吊るされるユーザー
    votecount++;
    if (votecount == apnum) {
      flag = 1;
    }
    if (voted == -1) { // 再投票へ
      revoteflag = 1;
      String username;
      for (int i = 0; i < num; i++) {
        int dora = userMapper.getDora("user" + (i + 1));
        if (dora == 0) {
          username = "user" + (i + 1);
          model.addAttribute("user" + (i + 1), username);
        }
      }

      model.addAttribute("revote", voted);
    } else if (voted <= 10) { // 吊るす人決定
      voteduser = "user" + voted;
      model.addAttribute("voted", voteduser);
    }
    return "vote.html";
  }

  @GetMapping("/revoting") // 再投票
  public String revoting(ModelMap model) {
    Vote voting = new Vote();
    int voted = voting.Voting(recountUser); // 吊るされるユーザー

    if (voted == -1) {
      model.addAttribute("endvote", voted);
    } else if (voted <= 10) {
      voteduser = "user" + voted;
      model.addAttribute("voted", voteduser);
    }

    return "vote.html";
  }

  @GetMapping("/vote/{num}")
  public String voteuser(@PathVariable Integer num) {
    if (revoteflag == 0) {
      countUser[num - 1]++;
    } else {
      recountUser[num - 1]++;
    }
    return "vote.html";
  }

  @GetMapping("/voteresult")
  public SseEmitter voteresult() {
    final SseEmitter sseEmitter = new SseEmitter();
    if (flag == 0) {
      this.acroom.vote(sseEmitter, apnum, countUser, flag);
    } else if(flag == 1){
      this.acroom.vote(sseEmitter, apnum, recountUser, flag);
    }
    return sseEmitter;
  }

}
