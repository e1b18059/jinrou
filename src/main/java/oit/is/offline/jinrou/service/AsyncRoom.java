package oit.is.offline.jinrou.service;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.beans.factory.annotation.Autowired;

import oit.is.offline.jinrou.model.Room;

@Service
public class AsyncRoom {
  int count = 1;
  int usercount = 0;
  String firstname;
  int time = 10;

  @Autowired
  Room room;

  @Async
  public void playercount(SseEmitter emitter) {
    try {
      while (true) {// 無限ループ
        TimeUnit.MILLISECONDS.sleep(500);
        if (room.users.size() < 2) {
          continue;
        }
        emitter.send(count);
      }
    } catch (Exception e) {
      // emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
    } finally {
      emitter.complete();
    }
  }

  @Async
  public void time(SseEmitter emitter, String user) {
    usercount++;
    if (usercount == 1) {
      firstname = user;
    }
    while (true) {
      try {
        TimeUnit.SECONDS.sleep(1);// 1秒STOP
        emitter.send(time);
        if (user == firstname) {
          time--;
        }
        if (time < 0) {
          time = 0;
          emitter.send(time);
          break;
        }
      } catch (Exception e) {
        // 例外の名前とメッセージだけ表示する
        emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
        break;
      }
    }
  }

  public void resetTime() {
    time = 10;
    usercount = 0;
    firstname = "";
  }

}
