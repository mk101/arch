package kolesov.maxim.server.repository;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface DataRepository<T> {

    List<T> findAll();

    T save(T data);

}
