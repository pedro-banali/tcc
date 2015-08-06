package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class UsuarioDAO {
	public boolean login(Usuario usuario) {
		
		

		Connection cf = ConnectionFactory.getConnection();
		
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM USUARIO where Login = ? AND Senha = ? LIMIT 0, 1000;";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				
				preparedStatement.setString(1, usuario.getLogin());
				preparedStatement.setString(2, usuario.getSenha());
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
		
					
					return true;
					
									
				}
				cf.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
