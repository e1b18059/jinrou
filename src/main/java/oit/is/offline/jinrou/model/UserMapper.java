package oit.is.offline.jinrou.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

  @Update("UPDATE JINROU SET USERNAME = #{name} WHERE ID = #{ran}")
  void update(User user);

  @Select("SELECT NAME FROM JINROU WHERE USERNAME = #{name}")
  String getRole(String name);

  @Select("SELECT USERNAME FROM JINROU WHERE USERNAME != #{name} AND USERNAME IS NOT NULL AND NAME = '人狼'")
  String getwerewolf(String name);

  @Select("SELECT USERNAME FROM JINROU WHERE USERNAME IS NOT NULL AND DORA = 0 ORDER BY USERNAME")
  ArrayList<String> getplayer();

  @Update("UPDATE JINROU SET DORA = 1 WHERE USERNAME = #{name}")
  void vote(String name);

  @Select("SELECT SYUZOKU FROM JINROU WHERE USERNAME = #{name}")
  int fortune(String name);

  @Select("SELECT SYUZOKU FROM JINROU WHERE USERNAME = #{name}")
  int shaman(String name);

  @Select("SELECT USERNAME FROM JINROU WHERE USERNAME IS NOT NULL AND DORA = 1 ORDER BY USERNAME")
  ArrayList<String> getdead();

  @Update("UPDATE JINROU SET GUARD = 1 WHERE USERNAME = #{name}")
  void knight(String name);

  @Update("UPDATE JINROU SET DORA = 1 WHERE USERNAME = #{name}")
  void werewolf(String name);

  @Select("SELECT DORA FROM JINROU WHERE USERNAME = #{name}")
  int getDora(String name);

  @Select("SELECT GUARD FROM JINROU WHERE USERNAME = #{name}")
  int getGuard(String name);

  @Update("UPDATE JINROU SET GUARD = 0 WHERE GUARD = 1")
  void initGuard();

  @Select("SELECT COUNT(*) FROM JINROU WHERE USERNAME IS NOT NULL AND SYUZOKU = 1 AND DORA = 0")
  int getHumanAlive(); // 生きている村人の人数

  @Select("SELECT COUNT(*) FROM JINROU WHERE USERNAME IS NOT NULL AND SYUZOKU = 2 AND DORA = 0")
  int getJinrouAlive(); // 生きている人狼の人数

  @Update("UPDATE JINROU SET USERNAME = NULL WHERE USERNAME IS NOT NULL")
  void initUser(); // 参加者の名前の初期化

  @Update("UPDATE JINROU SET DORA = 0 WHERE DORA = 1")
  void initDora(); // 生死情報の初期化
}
