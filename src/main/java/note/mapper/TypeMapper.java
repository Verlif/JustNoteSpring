package note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.model.Type;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {
}
