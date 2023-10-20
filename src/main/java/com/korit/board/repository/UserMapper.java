package com.korit.board.repository;

import com.korit.board.entity.User;
import org.apache.ibatis.annotations.Mapper;

// DB와연결 1.JDBC : 쌩으로 코드 작성 2.Mapper방식 : Mapping  3.ORM(객체 관리) : JPA
@Mapper // -> xml 경로 정해준 곳을 찾아가서 xml파일을 IoC에 등록 가능한 class 파일로 컴파일 함
public interface UserMapper {
    public int saveUser(User user);
    public int duplicateUser(User user);
    public User findUserByEmail(String email);
    public int updateEnabledToEmail(String email);
    public int updateProfileUrl(User user);
    public int updatePassword(User user);
}
