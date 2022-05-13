package DAO;

import DAOImpl.DepartmentDAOjdbc;
import DAOImpl.SellerDaoJdbc;
import config.DB;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDaoJdbc(DB.conectaDb());
    }

    public static DepartamentDAO createDepartmentDAO(){
        return new DepartmentDAOjdbc(DB.conectaDb());
    }
}
