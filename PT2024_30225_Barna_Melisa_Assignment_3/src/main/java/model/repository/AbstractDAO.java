package model.repository;

import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Cofetarie;
import model.Prajitura;
import model.connection.ConnectionFactory;

import java.lang.reflect.ParameterizedType;

public class AbstractDAO<T> implements Serializable {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;


    public AbstractDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public T findById(int id, String primaryKeyColumnName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + primaryKeyColumnName + " = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> result = createObjects(resultSet);
            if (!result.isEmpty())
                return result.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO " + type.getSimpleName() + " (";
        for (Field field : type.getDeclaredFields()) {
            query = query + field.getName() + ", ";
        }
        query = query.substring(0, query.length() - 2);
        query = query + ") VALUES (";
        for (Field field : type.getDeclaredFields()) {
            query = query + "?, ";
        }
        query = query.substring(0, query.length() - 2);
        query = query + ")";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                statement.setObject(i, field.get(t));
                i++;
            }
            statement.executeUpdate();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public boolean update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = null;

        try {
            connection = ConnectionFactory.getConnection();

            if (t instanceof Cofetarie) {
                Cofetarie client = (Cofetarie) t;
                query = "UPDATE cofetarie SET adresa_cofetarie = ? WHERE id_cofetarie = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, client.getAddress());
                statement.setInt(2, client.getId());
            } else if (t instanceof Prajitura) {
                Prajitura prajitura = (Prajitura) t;
                query = "UPDATE prajitura SET nume_prajitura = ?, descriere = ?, cofetarie_id = ?, pret = ?, data_expirare = ?, data_productie = ?, imagine = ? WHERE prajitura_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, prajitura.getNume_prajitura());
                statement.setString(2, prajitura.getDescriere());
                statement.setInt(3, prajitura.getCofetarie_id());
                statement.setBigDecimal(4, prajitura.getPret());
                statement.setTimestamp(5, Timestamp.valueOf(prajitura.getData_productie().atStartOfDay()));
                statement.setTimestamp(6, Timestamp.valueOf(prajitura.getData_expirare().atStartOfDay()));
                statement.setString(7,prajitura.getImagine());
                statement.setInt(8, prajitura.getId());
            } else {
                // Handle other types if needed
                return false;
            }

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, t.getClass().getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }


    public boolean delete(int id, String columnName) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE " + columnName + " = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
           LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Constructor<T> ctor = type.getDeclaredConstructor();
                ctor.setAccessible(true);
                T instance = ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);

                    // Dacă este un câmp LocalDate, convertește-l din java.sql.Date
                    if (value instanceof java.sql.Date) {
                        value = ((java.sql.Date) value).toLocalDate();
                    }

                    field.setAccessible(true);
                    field.set(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException | NoSuchMethodException | SecurityException e) {
            LOGGER.log(Level.WARNING, "Error creating objects: " + e.getMessage());
        }
        return list;
    }


    public String displayObjectsFromDatabase(String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    stringBuilder.append(metaData.getColumnLabel(i))
                            .append(": ")
                            .append(resultSet.getObject(i))
                            .append(", ");
                }
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                stringBuilder.append("\n");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:displayObjectsFromDatabase - SQLException: " + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public T findByName(String name, String columnName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + columnName + " = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            List<T> result = createObjects(resultSet);
            if (!result.isEmpty())
                return result.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
