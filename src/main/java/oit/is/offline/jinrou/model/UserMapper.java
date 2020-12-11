package oit.is.offline.jinrou.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

  @Update("UPDATE JINROU SET USERNAME = #{name} WHERE ID = #{ran}")
  void update(User user);

  @Select("SELECT NAME FROM JINROU WHERE USERNAME = #{name}")
  String getUser(String name);

  @Select("SELECT USERNAME FROM JINROU WHERE USERNAME IS NOT NULL AND DORA = 0 ORDER BY USERNAME")
  ArrayList<String> getvote();

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
}
