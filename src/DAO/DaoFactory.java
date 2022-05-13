package DAO;

import DAOImpl.SellerDaoJdbc;
import config.DB;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDaoJdbc(DB.conectaDb());
    }
}
