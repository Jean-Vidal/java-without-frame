package application;

import config.DB;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DB.conectaDb();















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