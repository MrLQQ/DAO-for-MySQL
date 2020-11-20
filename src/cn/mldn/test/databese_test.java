package cn.mldn.test;

import cn.mldn.dbc.DatabaseConnection;
import cn.mldn.factory.ServiceFactory;
import cn.mldn.impl.EmpDAOImpl;
import cn.mldn.vo.Emp;

import javax.xml.crypto.Data;
import java.security.Provider;
import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class databese_test {
    public static void main(String[] args) throws Exception {
        Emp vo=new Emp();
        vo.setEname("lqq");
        vo.setEmpno(002);
        vo.setJob("职工");
        vo.setHiredate(new Date());
        vo.setSal(1000.0);
        vo.setComm(200.0);

        try {
            System.out.println(ServiceFactory.getIEmpServiceInstance().insert(vo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            List<Emp>list=ServiceFactory.getIEmpServiceInstance().list();
            for (Emp emp:list){
                System.out.println(emp.getEmpno());
                System.out.println(emp.getEname());
                System.out.println(emp.getHiredate());
                System.out.println(emp.getComm());
                System.out.println(emp.getJob());
                System.out.println(emp.getSal());
            }
        }catch (Exception e){

        }
    }

}
