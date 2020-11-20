package cn.mldn.factory;

import cn.mldn.dao.IEmpDAO;
import cn.mldn.impl.EmpDAOImpl;

import java.sql.Connection;

public class DAOFactory {
    public static IEmpDAO getIEmpDAOInstance(Connection conn){
        return new EmpDAOImpl(conn);
    }
}
