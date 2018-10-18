package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.daw.bean.TipousuarioBean;

//        http://localhost:8081/trolleyes/json?op=getCount&ob=tipousuario
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
//            if (oResultSet.next()) {
                contador = oResultSet.getInt(1);
//            }

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
