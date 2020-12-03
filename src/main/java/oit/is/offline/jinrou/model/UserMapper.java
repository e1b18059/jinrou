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
}
