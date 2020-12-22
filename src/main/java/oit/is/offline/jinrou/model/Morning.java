package oit.is.offline.jinrou.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Morning {
  public ArrayList<String> users = new ArrayList<String>();

  public void addUser(String name) {
    // 同名のユーザが居たら何もせずにreturn
    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    // 同名のユーザが居なかった場合はusersにnameを追加する
    this.users.add(name);
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }

  public void initUser() { // 配列usersの初期化
    users.clear();
  }
}
