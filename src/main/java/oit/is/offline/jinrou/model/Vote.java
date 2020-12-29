package oit.is.offline.jinrou.model;

public class Vote {

  public int Voting(int num, int[] count) {
    int vc = 0; // 投票した人数 num->生きている人数
    int s = 0; // maxの時のユーザー番号
    int es = 0;

    for (int i = 0; i < 10; i++) {
      vc += count[i];
    }

    if (vc == num) {
      int max = count[0];

      for (int j = 0; j < 10; j++) { // maxを見つける
        if (count[j] >= max) {
          max = count[j];
          s = j + 1;
        }
      }

      for (int j = 0; j < 10; j++) { // maxと同じ添え字はあるか
        if (count[j] == max) {
          es++;
        }
      }

      if (es == 1) {
        return s;
      }
      if (es != 1) {
        return -1;
      }
    }

    return 11;
  }
}
