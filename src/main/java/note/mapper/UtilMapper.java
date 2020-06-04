package note.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UtilMapper {
    int checkTableExist(@Param("tableName") String tableName);
    int createTNote();
    int createTRecord();
    int createTUser();
    int createTShare();
    int createTType();
    int addForeignKey();
}
