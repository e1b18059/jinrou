package oit.is.offline.jinrou.model;

import java.util.Random;

public class RandomRole {
  Random random = new Random();
  int ran;
  int flag[] = { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

  public int Random(int num) {
    while (true) {
      ran = random.nextInt(num) + 1; // 0~(num-1)の乱数を生成した後に+1 -->1~num
      if (flag[ran] == 0) {
        flag[ran] = 1;
        break;
      }
    }
    return ran;
  }

  public void initRondom() {
    flag[0] = -1;
    for (int i = 1; i < flag.length; i++) {
      flag[i] = 0;
    }
  }

}
