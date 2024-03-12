package lol.koblizek.craftorm.persistence;

/**
 * Interface for automatic or manual object conversion to DTO
 */
public interface DtoConvertible<T> {
    default T toDto() {
        // TODO: Implement this method, so it will be converted to custom DTO class
        // Preferably using ASM?
        return (T) new Object();
    }
}
