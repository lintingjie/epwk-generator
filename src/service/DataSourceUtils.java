package service;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;


/**
 * Created by Greg.Chen on 2015/3/17.
 */
public class DataSourceUtils {

    private static DataSource ds;

    static{
        ds = new ComboPooledDataSource();
    }

    public static DataSource getDataSource(){
        return ds;
    }

    public static Connection getConn(){
        Connection con = null;
        try{
            con = ds.getConnection();//每一次从ds中获取一个新的连接
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

}
