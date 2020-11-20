package cn.mldn.impl;

import cn.mldn.dao.IEmpDAO;
import cn.mldn.vo.Emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EmpDAOImpl implements IEmpDAO {
    private Connection conn; //需要利用Connection对象操作
    private PreparedStatement pstmt;

    /**
     * 如果要想使用数据层进行原子性的功能操作实现，必须要提供有Connection接口对象
     * 另外，由于开发之中业务层要调用数据层，所以数据库的打开与关闭交由业务层处理
     * @param conn 表示数据库连接对象
     */
    public EmpDAOImpl(Connection conn){
        this.conn=conn;
    }

    @Override
    public boolean doCreate(Emp vo) throws Exception {
        String sql="insert into emp(empno,ename,job,hiredate,sal,comm) values (?,?,?,?,?,?)";
        this.pstmt=this.conn.prepareStatement(sql);
        this.pstmt.setInt(1,vo.getEmpno());
        this.pstmt.setString(2,vo.getEname());
        this.pstmt.setString(3,vo.getJob());
        this.pstmt.setDate(4,new java.sql.Date(vo.getHiredate().getTime()));
        this.pstmt.setDouble(5,vo.getSal());
        this.pstmt.setDouble(6,vo.getComm());
        return this.pstmt.executeUpdate()>0;
    }

    @Override
    public boolean doUpdate(Emp vo) throws Exception {
        String sql="update emp set ename=?,job=?,hiredate=?,sal=?,comm=? where empno=?";
        this.pstmt=this.conn.prepareStatement(sql);
        this.pstmt.setString(1,vo.getEname());
        this.pstmt.setString(2,vo.getJob());
        this.pstmt.setDate(3,new java.sql.Date(vo.getHiredate().getTime()));
        this.pstmt.setDouble(4,vo.getSal());
        this.pstmt.setDouble(5,vo.getComm());
        this.pstmt.setInt(6,vo.getEmpno());
        return this.pstmt.executeUpdate()>0;
    }

    @Override
    public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
        if(ids==null||ids.size()==0){   //没有要删除的数据
            return false;
        }
        StringBuffer sql=new StringBuffer();
        //sql:delete from 表名 where 关键字 in(值,值,值,值)
        sql.append("delete from emp where empno in(");
        Iterator<Integer> iter=ids.iterator();
        while (iter.hasNext()){         //每个值之间加逗号
            sql.append(iter.next()).append(",");
        }
        sql.delete(sql.length()-1,sql.length()).append(")");    //去掉结尾逗号加上口号
        this.pstmt=this.conn.prepareStatement(sql.toString());
        return this.pstmt.executeUpdate()==ids.size();
    }

    @Override
    public Emp findById(Integer id) throws Exception {
        String sql="select empno,ename,job,hiredate,sal,comm from emp where empno=?";
        Emp vo = new Emp();
        this.pstmt=this.conn.prepareStatement(sql);
        this.pstmt.setInt(1,id);
        ResultSet rs=this.pstmt.executeQuery();
        if(rs.next()){
            vo.setEmpno(rs.getInt(1));
            vo.setEname(rs.getString(2));
            vo.setJob(rs.getString(3));
            vo.setHiredate(rs.getDate(4));
            vo.setSal(rs.getDouble(5));
            vo.setComm(rs.getDouble(6));
        }
        return vo;
    }

    @Override
    public List<Emp> findALL() throws Exception {
        List<Emp> all=new ArrayList<Emp>();
        String sql="select empno,ename,job,hiredate,sal,comm from emp";
        this.pstmt=this.conn.prepareStatement(sql);
        ResultSet rs=this.pstmt.executeQuery();
        while (rs.next()){
            Emp vo = new Emp();
            vo.setEmpno(rs.getInt(1));
            vo.setEname(rs.getString(2));
            vo.setJob(rs.getString(3));
            vo.setHiredate(rs.getDate(4));
            vo.setSal(rs.getDouble(5));
            vo.setComm(rs.getDouble(6));
            all.add(vo);
        }
        return all;
    }

    @Override
    public List<Emp> fundAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        List<Emp> all=new ArrayList<Emp>();
        String sql="select * from " +
                " (select empno,ename,job,hiredate,sal,comm,ROWNUM rn" +
                " from emp" +
                " where "+column +" like ? and ROWNUM<=?) temp " +
                " where temp.rn>?";
        this.pstmt=this.conn.prepareStatement(sql);
        this.pstmt.setString(1,"%"+keyWord+"%");
        this.pstmt.setInt(2,currentPage*lineSize);
        this.pstmt.setInt(3,(currentPage-1)*lineSize);
        ResultSet rs=this.pstmt.executeQuery();
        while (rs.next()){
            Emp vo = new Emp();
            vo.setEmpno(rs.getInt(1));
            vo.setEname(rs.getString(2));
            vo.setJob(rs.getString(3));
            vo.setHiredate(rs.getDate(4));
            vo.setSal(rs.getDouble(5));
            vo.setComm(rs.getDouble(6));
            all.add(vo);
        }
        return all;
    }

    @Override
    public Integer getAllCount(String column, String KeyWord) throws Exception {
        String sql="select count(empno) from emp where "+ column +" like ?";
        this.pstmt=this.conn.prepareStatement(sql);
        this.pstmt.setString(1,"%"+KeyWord +"%");
        ResultSet rs=this.pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return null;
    }
}