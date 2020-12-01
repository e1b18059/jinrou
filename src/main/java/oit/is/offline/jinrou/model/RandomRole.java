package oit.is.offline.jinrou.model;

import java.util.Random;
import java.util.ArrayList;

public class RandomRole{
  Random random = new Random();

  Room player = new Room();
  player.addUser("qwertyu");
  System.out.println(player.users.size());
}
