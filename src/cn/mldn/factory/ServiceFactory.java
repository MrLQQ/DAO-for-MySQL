package cn.mldn.factory;

import cn.mldn.service.IEmpService;
import cn.mldn.service.impl.EmpServiceImpl;

public class ServiceFactory {
    public static IEmpService getIEmpServiceInstance(){
        return new EmpServiceImpl();
    }
}
