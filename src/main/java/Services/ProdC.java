package Services;

import java.sql.SQLException;
import java.util.List;

public interface ProdC <T>{
    void insert(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    void Delete (T t) throws SQLException;

    List<T> selectAll() throws SQLException;

}
