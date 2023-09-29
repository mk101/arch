package kolesov.maxim.server.repository;

public interface ImageRepository {

    void upload(String name, byte[] bytes);

    byte[] get(String name);

}
