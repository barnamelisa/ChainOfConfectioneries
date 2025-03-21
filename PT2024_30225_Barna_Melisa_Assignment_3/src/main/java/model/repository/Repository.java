package model.repository;

import model.Cofetarie;
import model.Prajitura;
import model.connection.Connection;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.ParameterizedType;

public class Repository<T> implements Serializable {
    protected static final Logger LOGGER = Logger.getLogger(Repository.class.getName());
    private final Class<T> type;

    public Repository() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public T findById(int id, String primaryKeyColumnName) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + primaryKeyColumnName + " = ?";

        try {
            connection = Connection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> result = createObjects(resultSet);
            if (!result.isEmpty())
                return result.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            Connection.close(resultSet);
            Connection.close(statement);
            Connection.close(connection);
        }
        return null;
    }

    public boolean updateStock(int prajituraId, int cofetarieId, int newStock) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE prajitura_cofetarie SET stoc = ? WHERE prajitura_id = ? AND id_cofetarie = ?";

        try {
            connection = Connection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, newStock);
            statement.setInt(2, prajituraId);
            statement.setInt(3, cofetarieId);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updating stock: " + e.getMessage());
        } finally {
            Connection.close(statement);
            Connection.close(connection);
        }
        return false;
    }

    public T insert(T t) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO " + type.getSimpleName() + " (";

        for (Field field : type.getDeclaredFields()) {
            query += field.getName() + ", ";
        }
        query = query.substring(0, query.length() - 2) + ") VALUES (";
        for (Field field : type.getDeclaredFields()) {
            query += "?, ";
        }
        query = query.substring(0, query.length() - 2) + ")";

        try {
            connection = Connection.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                statement.setObject(i, field.get(t));
                i++;
            }
            statement.executeUpdate();

            if (t instanceof Prajitura) {
                Prajitura prajitura = (Prajitura) t;

                try (PreparedStatement stmt2 = connection.prepareStatement(
                        "INSERT INTO prajitura_cofetarie (prajitura_id, id_cofetarie) VALUES (?, ?)")) {
                    stmt2.setInt(1, prajitura.getId());
                    stmt2.setInt(2, prajitura.getCofetarie_id());

                    stmt2.executeUpdate();
                }
            }

            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            Connection.close(resultSet);
            Connection.close(statement);
            Connection.close(connection);
        }
        return null;
    }

    public void savePrajituraToDoc(List<Prajitura> prajituri, String fileName) {
        XWPFDocument document = new XWPFDocument();

        try {
            XWPFParagraph title = document.createParagraph();
            title.createRun().setText("Prajitura Details:");

            for (Prajitura prajitura : prajituri) {
                XWPFParagraph paragraph = document.createParagraph();
                paragraph.createRun().setText("ID: " + prajitura.getId());
                paragraph = document.createParagraph();
                paragraph.createRun().setText("Nume: " + prajitura.getNume_prajitura());
                paragraph = document.createParagraph();
                paragraph.createRun().setText("Preț: " + prajitura.getPret());
                paragraph = document.createParagraph();
                paragraph.createRun().setText("Cofetarie ID: " + prajitura.getCofetarie_id());
                paragraph = document.createParagraph();
                paragraph.createRun().setText("----------"); // Linie de separare între prăjituri
            }

            try (FileOutputStream out = new FileOutputStream(fileName)) {
                document.write(out);
            }
            System.out.println("Prajituri saved to DOC file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving prajituri to DOC: " + e.getMessage());
        }
    }


    public boolean update(T t) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        String query = null;

        try {
            connection = Connection.getConnection();

            if (t instanceof Cofetarie) {
                Cofetarie client = (Cofetarie) t;
                query = "UPDATE cofetarie SET adresa_cofetarie = ? WHERE id_cofetarie = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, client.getAddress());
                statement.setInt(2, client.getId());
            } else if (t instanceof Prajitura) {
                Prajitura prajitura = (Prajitura) t;
                query = "UPDATE prajitura SET nume_prajitura = ?, descriere = ?, cofetarie_id = ?, pret = ?, data_productie = ?, data_expirare = ?, imagine = ? WHERE prajitura_id = ?";
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
            Connection.close(statement);
            Connection.close(connection);
        }
        return false;
    }


    public boolean delete(int id, String columnName) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE " + columnName + " = ?";
        try {
            connection = Connection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
           LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            Connection.close(statement);
            Connection.close(connection);
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

        try (java.sql.Connection connection = Connection.getConnection();
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

    public String findCakeByName(String cakeName) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT * FROM prajitura WHERE nume_prajitura = ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Setăm parametrul de interogare cu numele prăjiturii căutate
            preparedStatement.setString(1, cakeName);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Procesăm fiecare rând din rezultat
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
            LOGGER.log(Level.WARNING, "findCakeByName - SQLException: " + e.getMessage());
        }

        return stringBuilder.toString();
    }

    public List<Prajitura> findCakesByAvailability(int idCofetarie) {
        List<Prajitura> cakes = new ArrayList<>();
        String query = "SELECT p.* FROM prajitura p " +
                "JOIN prajitura_cofetarie pc ON p.prajitura_id = pc.prajitura_id " +
                "WHERE pc.id_cofetarie = ? AND pc.stoc > 0"; // Verificăm stocul > 0 în tabela prajitura_cofetarie

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idCofetarie);
            ResultSet resultSet = preparedStatement.executeQuery();
            cakes = (List<Prajitura>) createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "CakeDAO:findCakesByAvailability " + e.getMessage());
        }

        return cakes;
    }

    public List<Prajitura> findExpiredOrOutOfStockCakes(int cofetarieId, LocalDate currentDate) {
        List<Prajitura> cakes = new ArrayList<>();
        String query = "SELECT p.* FROM prajitura p " +
                "JOIN prajitura_cofetarie pc ON p.prajitura_id = pc.prajitura_id " +
                "WHERE (pc.stoc = 0 OR p.data_expirare < ?) " +
                "AND pc.id_cofetarie = ?";  // Adăugăm filtrul pentru ID-ul cofetăriei

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, Date.valueOf(currentDate));  // Setăm data curentă
            preparedStatement.setInt(2, cofetarieId);  // Setăm ID-ul cofetăriei pentru filtrare
            ResultSet resultSet = preparedStatement.executeQuery();
            cakes = (List<Prajitura>) createObjects(resultSet);  // Crearea obiectelor din rezultatul interogării

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "findExpiredOrOutOfStockCakes " + e.getMessage());
        }

        return cakes;
    }


    public void createCSV(List<Prajitura> expiredOrOutOfStockCakes) {
        String fileName = "expired_or_out_of_stock_cakes.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            // Scriem header-ul
            writer.append("ID, Nume, Descriere, Data Expirare, Stoc\n");

            // Scriem fiecare prăjitură
            for (Prajitura prajitura : expiredOrOutOfStockCakes) {
                writer.append(prajitura.getId() + ", ");
                writer.append(prajitura.getNume_prajitura() + ", ");
                writer.append(prajitura.getDescriere() + ", ");
                writer.append(prajitura.getData_expirare().toString() + ", ");

            }

            System.out.println("CSV created successfully: " + fileName);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error creating CSV file: " + e.getMessage());
        }
    }

    public List<Prajitura> findCakesByValidity(int idCofetarie, LocalDate currentDate) {
        List<Prajitura> cakes = new ArrayList<>();
        String query = "SELECT p.* FROM prajitura p " +
                "JOIN prajitura_cofetarie pc ON p.prajitura_id = pc.prajitura_id " +
                "WHERE pc.id_cofetarie = ? AND p.data_expirare >= ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idCofetarie);
            preparedStatement.setDate(2, Date.valueOf(currentDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            cakes = (List<Prajitura>) createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "CakeDAO:findCakesByValidity " + e.getMessage());
        }

        return cakes;
    }

    public boolean checkCofetarieExists(int cofetarieId) {
        java.sql.Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT 1 FROM prajitura_cofetarie WHERE id_cofetarie = ? LIMIT 1";

        try {
            connection = Connection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, cofetarieId);
            resultSet = statement.executeQuery();

            // Dacă rezultatul conține cel puțin un rând, înseamnă că cofetăria există
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:checkCofetarieExists " + e.getMessage());
        } finally {
            Connection.close(resultSet);
            Connection.close(statement);
            Connection.close(connection);
        }
        return false;
    }
}
