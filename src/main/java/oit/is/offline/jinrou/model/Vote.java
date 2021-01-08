package oit.is.offline.jinrou.model;

public class Vote {

  public int Voting(int num, int[] count) {
    int s = 0; // maxの時のユーザー番号
    int es = 0;

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
    else {
      return -1;
    }
  }
}
