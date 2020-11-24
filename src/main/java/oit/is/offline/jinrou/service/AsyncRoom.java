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
        System.out.println(count);
        emitter.send(count);
      }
    } catch (Exception e) {
      //emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
    }finally{
      emitter.complete();
    }
  }

}
