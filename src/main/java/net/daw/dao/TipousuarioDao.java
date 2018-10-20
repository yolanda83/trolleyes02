package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.daw.bean.TipousuarioBean;
//http://localhost:8081/trolleyes/json?op=get&ob=tipousuario&id=1
//http://localhost:8081/trolleyes/json?op=getCount&ob=tipousuario
//http://localhost:8081/trolleyes/json?op=create&ob=tipousuario&desc=Cliente2
//http://localhost:8081/trolleyes/json?op=update&ob=tipousuario&id=2&desc=Proveedor
//http://localhost:8081/trolleyes/json?op=remove&ob=tipousuario&id=1

public class TipousuarioDao {

    Connection oConnection;
    String ob = "tipoUsuario";

    public TipousuarioDao(Connection oConnection) {
        super();
        this.oConnection = oConnection;
    }

    public TipousuarioBean get(int id) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        TipousuarioBean oTipousuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oTipousuarioBean = new TipousuarioBean();
                oTipousuarioBean.setId(oResultSet.getInt("id"));
                oTipousuarioBean.setDesc(oResultSet.getString("desc"));
            } else {
                oTipousuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de tipousuario", e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oTipousuarioBean;
    }

    public int getCount() throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        int contador = 0;

        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                contador = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao getCount de tipousuario", e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return contador;
    }

    public int getMaxId() throws Exception {
        String strSQL = "SELECT MAX(id) FROM " + ob;
        int idRes = 0;

        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                idRes = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao getMaxId de tipousuario", e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        idRes += 1; //le sumamos uno para obtener el siguiente id disponible
        return idRes;
    }

    public boolean create(TipousuarioBean oTipousuario) throws Exception {
        StringBuilder sql = new StringBuilder();
        // Creamos la sentencia SQL INSERT
        sql.append("INSERT INTO `tipousuario`").
                append("(`id`,`desc`) ").
                append("VALUES (?,?)");

        boolean estado = false;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(sql.toString());

            oPreparedStatement.setInt(1, oTipousuario.getId());
            oPreparedStatement.setString(2, oTipousuario.getDesc());
            oPreparedStatement.execute();
            estado = true;

        } catch (SQLException e) {
            throw new Exception("Error en Dao Create de tipousuario", e);
        } finally {

            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return estado;
    }

    public boolean update(TipousuarioBean oTipousuario) throws Exception {
        StringBuilder sql = new StringBuilder();
        // Creamos la sentencia SQL UPDATE
        sql.append("UPDATE `tipousuario` SET ").
                append("`desc` = ? ").
                append("WHERE id = ?");

        boolean estado = false;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(sql.toString());

            oPreparedStatement.setString(1, oTipousuario.getDesc());
            oPreparedStatement.setInt(2, oTipousuario.getId());
            oPreparedStatement.execute();
            estado = true;

        } catch (SQLException e) {
            throw new Exception("Error en Dao Update de tipousuario", e);
        } finally {

            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return estado;
    }

    public boolean remove(int id) throws Exception {
        String strSQL = "DELETE FROM " + ob + " WHERE id=?";
        boolean estado = false;
        PreparedStatement oPreparedStatement = null;

        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);

            oPreparedStatement.setInt(1, id);
            oPreparedStatement.execute();
            estado = true;

        } catch (SQLException e) {
            throw new Exception("Error en Dao remove de tipousuario", e);
        } finally {

            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return estado;
    }
}
