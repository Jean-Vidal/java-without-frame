package application;

import DAO.DaoFactory;
import DAO.DepartamentDAO;
import config.DB;
import entities.Department;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = DB.conectaDb();

        DepartamentDAO departamentDAO = DaoFactory.createDepartmentDAO();

        //Department department = new Department(5, "office");
        //departamentDAO.insert(department);

        //Department department = new Department(5, "builds");
        //departamentDAO.update(department);

        //Department department = departamentDAO.findById(2);
        //System.out.println(department);

        departamentDAO.deleteById(5);

        List<Department> list = departamentDAO.findAll();
        list.forEach(System.out::println);














        /*
        Seller
         */
        /*SellerDAO sellerDAO = DaoFactory.createSellerDao();
        //Seller seller = sellerDAO.findById(3);
        //System.out.println(seller);

        //Department department = new Department(2, null);
        //List<Seller> list = sellerDAO.findByDepartment(department);

        /*Seller seller = new Seller(9, "jean",
                "jean@gmail.com", new Date(), 5000.00, department);
        sellerDAO.update(seller);

        sellerDAO.deleteById(9);

        List<Seller> list = sellerDAO.findAll();

        list.forEach(System.out::println);*/
    }
}