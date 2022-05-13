package DAOImpl;

import DAO.DepartamentDAO;
import entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOjdbc implements DepartamentDAO {
    Connection connection;

    public DepartmentDAOjdbc(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement pr = null;
        String sql = "INSERT INTO department(Id, Name) " +
                     "VALUE (? , ?) ";

        try{
            pr = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setInt(1, obj.getId());
            pr.setString(2, obj.getName());

            int rows = pr.executeUpdate();

            if(rows > 0){
                ResultSet rs = pr.getGeneratedKeys();

                if(rs.next()){
                    int idr = rs.getInt(1);
                    obj.setId(idr);
                }
                System.out.println(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement pr = null;
        String sql = "UPDATE department " +
                     "SET Name = ? " +
                     "WHERE Id = ? ";
        try{
            pr = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, obj.getName());
            pr.setInt(2, obj.getId());

            int rows = pr.executeUpdate();
            if(rows > 0){
                ResultSet rs = pr.getGeneratedKeys();

                if(rs.next()){
                    int idR = rs.getInt(1);
                    obj.setId(idR);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "DELETE FROM department WHERE Id = ? ";

        try{
            pr = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setInt(1, id);

            int rows = pr.executeUpdate();

            if(rows > 0){
                rs = pr.getGeneratedKeys();

                if(rs.next()){
                    int idN = rs.getInt(1);
                    id = idN;
                }
                System.out.println(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM department WHERE Id = ?";

        Department department = new Department();

        try{
            pr = connection.prepareStatement(sql);
            pr.setInt(1, id);
            rs = pr.executeQuery();

            if (rs.next()){
                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement pr = null;
        ResultSet rs = null;

        List<Department> list = new ArrayList<>();

        String sql = "SELECT * FROM department ";

        try{
            pr = connection.prepareStatement(sql);
            rs = pr.executeQuery();

            while (rs.next()){
                Department department = new Department();
                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));

                list.add(department);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }
}
