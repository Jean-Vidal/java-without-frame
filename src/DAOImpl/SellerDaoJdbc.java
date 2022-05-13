package DAOImpl;

import DAO.SellerDAO;
import entities.Department;
import entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJdbc implements SellerDAO {

    private Connection con;

    public SellerDaoJdbc(Connection con){
        this.con = con;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement pr = null;
        String sql = "INSERT INTO seller( " +
                     "Name, " +
                     "Email, " +
                     "BirthDate, " +
                     "BaseSalary, " +
                     "DepartmentId) " +
                     "VALUE (?, ?, ?, ?, ? )";

        try{
            pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, obj.getName());
            pr.setString(2, obj.getEmail());
            pr.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            pr.setDouble(4, obj.getBaseSalary());
            pr.setInt(5, obj.getDepartment().getId());

            int rows = pr.executeUpdate();

            if(rows > 0){
                ResultSet rs = pr.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                System.out.println(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement pr = null;
        String sql = "UPDATE seller " +
                     "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                     "WHERE Id = ? ";

        try{
            pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, obj.getName());
            pr.setString(2, obj.getEmail());
            pr.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            pr.setDouble(4, obj.getBaseSalary());
            pr.setInt(5, obj.getDepartment().getId());
            pr.setInt(6, obj.getId());

           int rows = pr.executeUpdate();

           if(rows > 0){
               ResultSet rs = pr.getGeneratedKeys();
               if(rs.next()){
                   int id = rs.getInt(1);
                   obj.setId(id);
               }
               System.out.println(rs);
           }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pr = null;
        String sql = "DELETE FROM seller " +
                     "WHERE id = ? ";
        try{
            pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setInt(1, id);

            int rowsAffected = pr.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = pr.getGeneratedKeys();
                if(rs.next()){
                    int idCol = rs.getInt(1);
                    id = idCol;
                }

                System.out.println(id);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Seller findById(Integer id) {
        ResultSet rs = null;
        PreparedStatement pr = null;
        Seller obj = null;

        String query = "SELECT seller.*,department.Name as DepName " +
                       "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                       "WHERE seller.Id = ?";
        try{
            pr = con.prepareStatement(query);
            pr.setInt(1, id);
            rs = pr.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                obj = instantiateSeller(rs, dep);

                return obj;
            }
            return null;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public List<Seller> findAll() {
        ResultSet rs = null;
        PreparedStatement pr = null;

        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        String sql = "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                "ORDER BY Name ";

        try{
            pr = con.prepareStatement(sql);
            rs = pr.executeQuery();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        ResultSet rs = null;
        PreparedStatement pr = null;

        String sql = "SELECT seller.*,department.Name as DepName " +
                     "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                     "WHERE DepartmentId = ? " +
                     "ORDER BY Name ";

        try{
            pr = con.prepareStatement(sql);
            pr.setInt(1, department.getId());
            rs = pr.executeQuery();

            while(rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);

        return seller;
    }
}
