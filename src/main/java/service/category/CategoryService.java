package service.category;

import config.ConnectionJDBC;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService<Category>{


    private static final String SELECT_CATEGORIES_BY_ID = "SELECT *FROM categories WHERE id= ?";
    private static final String SELECT_ALL_CATEGORIES = "SELECT *FROM categories";
    Connection connection = ConnectionJDBC.getConnection();
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                categories.add(new Category(id,name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORIES_BY_ID);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String name = rs.getString(2);
                category = new Category(id,name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    @Override
    public void save(Category category) {

    }

    @Override
    public void update(Category category, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
