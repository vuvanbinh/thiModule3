package controller;

import model.Category;
import model.Product;
import service.category.CategoryService;
import service.category.ICategoryService;
import service.product.IProductService;
import service.product.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {

    IProductService productService = new ProductService();
    ICategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "create" :
                showCreateProductForm(request,response);
                break;
            case "edit" :
                showEditProductForm(request,response);
                break;
            case "delete" :
                showDeleteFrom(request,response);
                break;
            case "search":
                showSearchByProductName(request,response);
                break;
            default:
                showProductList(request,response);
        }
    }

    private void showSearchByProductName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("searchByName");
        List<Product> productList = productService.finByName(name);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/search.jsp");
        request.setAttribute("pl",productList);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showDeleteFrom(HttpServletRequest request, HttpServletResponse response) {
        String messenger = "Are you sure";
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = (Product) productService.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/delete.jsp");
        request.setAttribute("messenger",messenger);
        request.setAttribute("p",product);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = categoryService.findAll();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = (Product) productService.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
        request.setAttribute("p",product);
        request.setAttribute("ca",categories);

        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateProductForm(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = categoryService.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        request.setAttribute("ca",categories);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response) {
        List<Product> productList = productService.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/product.jsp");
        request.setAttribute("pl",productList);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "create" :
              createProductForm(request,response);
                break;
            case "edit" :
                editProductForm(request,response);
                break;
            case "delete" :
                deleteProduct(request,response);
                break;
            default:
//                showProductList(request,response);
        }


    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        try {
            response.sendRedirect("products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editProductForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        Category category = (Category) categoryService.findById(category_id);

        Product product = new Product(name,price,quantity,color,description,category);
        productService.update(product,id);
        try {
            response.sendRedirect("products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProductForm(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        Category category = (Category) categoryService.findById(category_id);

        Product product = new Product(name,price,quantity,color,description,category);

        productService.save(product);

        try {
            response.sendRedirect("products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
