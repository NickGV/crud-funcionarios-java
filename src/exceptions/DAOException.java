package exceptions;

/**
 * Excepción personalizada para operaciones de la capa DAO
 * Encapsula los errores de acceso a datos y proporciona mensajes significativos
 */
public class DAOException extends Exception {

  /**
   * Constructor con mensaje
   */
  public DAOException(String mensaje) {
    super(mensaje);
  }

  /**
   * Constructor con mensaje y causa
   */
  public DAOException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }

  /**
   * Constructor con causa
   */
  public DAOException(Throwable causa) {
    super(causa);
  }
}
