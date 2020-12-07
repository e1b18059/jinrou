package oit.is.offline.jinrou.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.beans.factory.annotation.Autowired;

import oit.is.offline.jinrou.model.Room;

@Service
public class AsyncRoom {
  int count = 1;

  @Autowired
  Room room;
  @Async
  public void count(SseEmitter emitter) {
    try {
      while (true) {// 無限ループ
        TimeUnit.MILLISECONDS.sleep(500);
        if(room.users.size() < 2){
          continue;
        }
        emitter.send(count);
      }
    } catch (Exception e) {
      //emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
    }finally{
      emitter.complete();
    }
  }

  int time = 10;

  @Async
  public void time(SseEmitter emitter, String user) {
    while (true) {// 無限ループ
      try {
        TimeUnit.SECONDS.sleep(1);// 1秒STOP
        emitter.send(time);// ここでsendすると引数をブラウザにpushする
        if(user=="user1") {
          time--;
          if(time==0) {
            emitter.send(time);// ここでsendすると引数をブラウザにpushする
          break;
          }
        }
      } catch (Exception e) {
        // 例外の名前とメッセージだけ表示する
        emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
        break;
      }
    }

  }

}
