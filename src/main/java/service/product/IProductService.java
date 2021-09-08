package service.product;

import model.Product;
import service.IService;

import java.util.List;

public interface IProductService<E> extends IService<E> {
    List<E> finByName(String name);
}
