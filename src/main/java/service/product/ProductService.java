package service.product;

import config.ConnectionJDBC;
import model.Category;
import model.Product;
import service.category.CategoryService;
import service.product.IProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService<Product> {


    public ProductService() {
    }

    private static final String SELECT_PRODUCTS_BY_ID = "SELECT * FROM products WHERE id =?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String II_PRODUCTS =
            "INSERT INTO products (name, price, quantity, color, description, category_id) VALUE (?,?,?,?,?,?);";
    private static final String UPDATE_PRODUCTS_BY_ID =
            "UPDATE products SET name =?,price=?, quantity=?, color=?, description=?, category_id=? WHERE id =?;";
    private static final String DELETE_PRODUCTS_BY_ID = "DELETE FROM products WHERE id= ?";
    private static final String SELECT_PRODUCTS_BY_NAME = "SELECT*FROM products WHERE name= ?";


    Connection connection = ConnectionJDBC.getConnection();
    CategoryService categoryService = new CategoryService();


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int price = rs.getInt(3);
                int quantity = rs.getInt(4);
                String color = rs.getString(5);
                String description = rs.getString(6);
                Category category = categoryService.findById(rs.getInt(7));
                products.add(new Product(id,name,price,quantity,color,description,category));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(int id) {
        Product product = new Product();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS_BY_ID);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String name = rs.getString(2);
                int price = rs.getInt(3);
                int quantity = rs.getInt(4);
                String color = rs.getString(5);
                String description = rs.getString(6);
                Category category = categoryService.findById(rs.getInt(7));
                product = new Product(id,name,price,quantity,color,description,category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    @Override
    public void save(Product p) {
        try {
            PreparedStatement statement = connection.prepareStatement(II_PRODUCTS);
            statement.setString(1,p.getName());
            statement.setInt(2,p.getPrice());
            statement.setInt(3,p.getQuantity());
            statement.setString(4,p.getColor());
            statement.setString(5,p.getDescription());
            statement.setInt(6,p.getCategory().getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Product p, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_BY_ID);
            statement.setString(1,p.getName());
            statement.setInt(2,p.getPrice());
            statement.setInt(3,p.getQuantity());
            statement.setString(4,p.getColor());
            statement.setString(5,p.getDescription());
            statement.setInt(6,p.getCategory().getId());
            statement.setInt(7,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_BY_ID);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public List<Product> finByName(String name) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS_BY_NAME);
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                int price = rs.getInt(3);
                int quantity = rs.getInt(4);
                String color = rs.getString(5);
                String description = rs.getString(6);
                Category category = categoryService.findById(rs.getInt(7));
                products.add(new Product(id,name,price,quantity,color,description,category));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }
}
